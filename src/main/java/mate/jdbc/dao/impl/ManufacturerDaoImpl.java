package mate.jdbc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        final String queryGetAll =
                "SELECT `id`, `name`, `country` FROM `manufacturers` WHERE `is_deleted` = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllStatement = connection.prepareStatement(queryGetAll)) {
            ResultSet resultSet = getAllStatement.executeQuery();
            Manufacturer manufacturer;
            while (resultSet.next()) {
                manufacturers.add(getManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get a list of manufacturers "
                    + "from manufacturers table.", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        final String createQuery =
                "INSERT INTO `manufacturers` (`name`, `country`) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createPreparedStatement =
                        connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)) {
            createPreparedStatement.setString(1, manufacturer.getName());
            createPreparedStatement.setString(2, manufacturer.getCountry());
            createPreparedStatement.executeUpdate();
            ResultSet generatedKeys = createPreparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                manufacturer.setId(generatedKeys.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't create manufacturer. " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String query = "SELECT `id`, `name`, `country` FROM `manufacturers` "
                + "WHERE `is_deleted` = FALSE AND `id` = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedGet = connection.prepareStatement(query)) {
            preparedGet.setLong(1, id);
            ResultSet resultSetManufacturer = preparedGet.executeQuery();
            Manufacturer manufacturer = null;
            if (resultSetManufacturer.next()) {
                manufacturer = getManufacturer(resultSetManufacturer);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get manufacturer by id " + id, e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String query =
                "UPDATE `manufacturers` SET `name` = ?, `country` = ? "
                        + " WHERE `id` = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updatePreparedStatement =
                        connection.prepareStatement(query)) {
            updatePreparedStatement.setString(1, manufacturer.getName());
            updatePreparedStatement.setString(2, manufacturer.getCountry());
            updatePreparedStatement.setLong(3, manufacturer.getId());
            updatePreparedStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't update a manufacturer "
                    + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteQuery =
                "UPDATE `manufacturers` SET `is_deleted` = '1' WHERE `id` = ?;";
        int count;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createPreparedStatement =
                        connection.prepareStatement(deleteQuery)) {
            createPreparedStatement.setLong(1, id);
            createPreparedStatement.executeUpdate();
            count = createPreparedStatement.getUpdateCount();
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't delete a manufacturer by id " + id, e);
        }
        return count > 0;
    }

    private Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        return new Manufacturer(id, name, country);
    }
}
