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
    public Manufacturer create(Manufacturer manufacturer) {
        if (manufacturer == null) {
            return null;
        }
        String query = "INSERT INTO manufacturers(name, country) VALUES(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement insertStatement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setString(1, manufacturer.getName());
            insertStatement.setString(2, manufacturer.getCountry());
            insertStatement.executeUpdate();
            ResultSet generatedKeys = insertStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                manufacturer.setId(generatedKeys.getObject(1, Long.class));
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t insert data to db. Params: name = "
                    + manufacturer.getName()
                    + " country = " + manufacturer.getCountry(), e);
        }
    }

    public Optional<Manufacturer> get(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement = connection.prepareStatement(
                        "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false")) {
            Manufacturer manufacturer = null;
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            if (resultSet.next()) {
                manufacturer = parseManufacturer(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t read data from db. Id = " + id, e);
        }
    }

    public List<Manufacturer> getAll() {
        List<Manufacturer> result = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllStatement = connection.createStatement()) {
            ResultSet resultSet = getAllStatement.executeQuery("SELECT * FROM manufacturers "
                    + "WHERE is_deleted = false");
            while (resultSet.next()) {
                result.add(parseManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t read all data from db", e);
        }
        return result;
    }

    public Manufacturer update(Manufacturer manufacturer) {
        if (manufacturer == null) {
            return null;
        }
        String query = "UPDATE manufacturers SET name = ?, "
                + "country = ? WHERE id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.setLong(3, manufacturer.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update data from db. Params: "
                    + manufacturer, e);
        }
        return manufacturer;
    }

    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteStatement = connection.prepareStatement(
                        "UPDATE manufacturers SET is_deleted = false WHERE id = ?")) {
            deleteStatement.setLong(1, id);
            return deleteStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete data from db. Id = " + id, e);
        }
    }

    private Manufacturer parseManufacturer(ResultSet resultSet) throws SQLException {
        Long currentId = resultSet.getObject(1, Long.class);
        String name = resultSet.getString(2);
        String country = resultSet.getString(3);
        return new Manufacturer(currentId, name, country);
    }
}
