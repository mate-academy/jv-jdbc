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
        Manufacturer manufacturer = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement = connection.prepareStatement(
                        "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false")) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            if (resultSet.next()) {
                manufacturer = parseManufacturer(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t read data from db. Id = " + id, e);
        }
        return Optional.of(manufacturer);
    }

    public List<Manufacturer> getAll() {
        List<Manufacturer> result = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                Statement readFromDB = connection.createStatement()) {
            ResultSet resultSet = readFromDB.executeQuery("SELECT * FROM manufacturers "
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
                PreparedStatement updateData = connection.prepareStatement(query)) {
            updateData.setString(1, manufacturer.getName());
            updateData.setString(2, manufacturer.getCountry());
            updateData.setLong(3, manufacturer.getId());
            updateData.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update data from db. Params: "
                    + manufacturer, e);
        }
        return manufacturer;
    }

    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                Statement deleteStatement = connection.createStatement()) {
            return deleteStatement.execute("UPDATE manufacturers "
                    + "SET is_deleted = 1 WHERE id = " + id);
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
