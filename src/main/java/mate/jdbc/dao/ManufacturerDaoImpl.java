package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        final String queryGetAll =
                "SELECT `id`, `name`, `country` from `manufacturer` WHERE `is_deleted` = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllStatement = connection.prepareStatement(queryGetAll)) {
            ResultSet resultSet = getAllStatement.executeQuery();
            Manufacturer manufacturer;
            while (resultSet.next()) {
                manufacturer = new Manufacturer();
                Long id = resultSet.getObject("id", Long.class);
                manufacturer.setId(id);
                manufacturer.setName(resultSet.getString("name"));
                manufacturer.setCountry(resultSet.getString("country"));
                manufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        final String createQuery =
                "INSERT INTO `manufacturer` (`name`, `country`) VALUES (?, ?);";
        Optional<Manufacturer> optionalManufacturer = Optional.empty();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createPreparedStatement =
                        connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)) {
            createPreparedStatement.setString(1, manufacturer.getName());
            createPreparedStatement.setString(2, manufacturer.getCountry());
            createPreparedStatement.executeUpdate();
            ResultSet generatedKeys = createPreparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                optionalManufacturer = get(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return optionalManufacturer.get();
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String queryGetManufacturerById = "SELECT `name`, `country` from `manufacturer` "
                + "WHERE `is_deleted` = false and `id` = ?;";
        Optional<Manufacturer> optionalManufacturer = Optional.empty();
        try (Connection connection = ConnectionUtil.getConnection();
                  PreparedStatement preparedGet =
                        connection.prepareStatement(queryGetManufacturerById)) {
            preparedGet.setLong(1, id);
            ResultSet resultSetManufacturer = preparedGet.executeQuery();
            if (resultSetManufacturer.next()) {
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(id);
                manufacturer.setName(resultSetManufacturer.getString("name"));
                manufacturer.setCountry(resultSetManufacturer.getString("country"));
                optionalManufacturer = Optional.of(manufacturer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return optionalManufacturer;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateQuery =
                "UPDATE `manufacturer` SET `name` = ?, `country` = ? "
                        + " WHERE `manufacturer`.`id` = ?;";
        int updateCount;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updatePreparedStatement =
                        connection.prepareStatement(updateQuery)) {
            updatePreparedStatement.setString(1, manufacturer.getName());
            updatePreparedStatement.setString(2, manufacturer.getCountry());
            updatePreparedStatement.setLong(3, manufacturer.getId());
            updatePreparedStatement.executeUpdate();
            updateCount = updatePreparedStatement.getUpdateCount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return updateCount > 0 ? get(manufacturer.getId()).get() : null;
    }

    @Override
    public Boolean delete(Long id) {
        String deleteQuery =
                "UPDATE `manufacturer` SET `is_deleted` = '1' WHERE `manufacturer`.`id` = ?;";
        int count;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createPreparedStatement =
                        connection.prepareStatement(deleteQuery)) {
            createPreparedStatement.setLong(1, id);
            createPreparedStatement.executeUpdate();
            count = createPreparedStatement.getUpdateCount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count > 0;
    }
}
