package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String query = "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer to DB: "
                    + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String query = "SELECT * FROM manufacturers WHERE id = ? "
                + "AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(parseManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufactures = new ArrayList<>();
        String query = "SELECT * FROM manufacturers WHERE is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery(query);
            while (resultSet.next()) {
                allManufactures.add(parseManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
        return allManufactures;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String query = "UPDATE manufacturers SET name = ?, "
                + "country = ? WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(query)) {
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.setLong(3, manufacturer.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update this manufacturer " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE manufacturers SET is_deleted = true "
                + "WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection
                           .prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete this manufacturer by id " + id, e);
        }
    }

    private Manufacturer parseManufacturer(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject(1, Long.class);
        String name = resultSet.getString(2);
        String country = resultSet.getString(3);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(id);
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        return manufacturer;
    }
}
