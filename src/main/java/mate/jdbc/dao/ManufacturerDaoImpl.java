package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.Manufacturer;
import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    public Manufacturer create(Manufacturer manufacturer) {
        if (manufacturer == null) {
            return null;
        }
        String query = "INSERT INTO manufacturers(name, country) VALUES(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement insertManufacturer = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)) {
            insertManufacturer.setString(1, manufacturer.getName());
            insertManufacturer.setString(2, manufacturer.getCountry());
            insertManufacturer.executeUpdate();
            ResultSet generatedKeys = insertManufacturer.getGeneratedKeys();
            if (generatedKeys.next()) {
                manufacturer.setId(generatedKeys.getObject(1, Long.class));
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t insert data to db", e);
        }
    }

    public Optional<Manufacturer> get(Long id) {
        Manufacturer manufacturer = null;
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getManufacturer = connection.createStatement()) {
            ResultSet resultSet = getManufacturer.executeQuery("SELECT * FROM manufacturers "
                    + "WHERE id = " + id);
            if (resultSet.next()) {
                Long currId = resultSet.getObject(1, Long.class);
                String name = resultSet.getString(2);
                String country = resultSet.getString(3);
                manufacturer = new Manufacturer(currId, name, country);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t read data from db", e);
        }
        return Optional.of(manufacturer);
    }

    public List<Manufacturer> getAll() {
        List<Manufacturer> result = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                Statement readFromDB = connection.createStatement()) {
            ResultSet resultSet = readFromDB.executeQuery("SELECT * FROM manufacturers "
                    + "WHERE is_deleted = 0");
            while (resultSet.next()) {
                Long id = resultSet.getObject(1, Long.class);
                String name = resultSet.getString(2);
                String country = resultSet.getString(3);
                Manufacturer manufacturer = new Manufacturer(id, name, country);
                result.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t read data from db", e);
        }
        return result;
    }

    public Manufacturer update(Manufacturer manufacturer) {
        if (manufacturer == null) {
            return null;
        }
        String query = "UPDATE manufacturers SET name = ?, country = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateData = connection.prepareStatement(query)) {
            updateData.setString(1, manufacturer.getName());
            updateData.setString(2, manufacturer.getCountry());
            updateData.setObject(3, manufacturer.getId(), JDBCType.INTEGER);
            updateData.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t read data from db", e);
        }
        return manufacturer;
    }

    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                Statement deleteStatement = connection.createStatement()) {
            return deleteStatement.execute("UPDATE manufacturers "
                    + "SET is_deleted = 1 WHERE id = " + id);
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete data from db", e);
        }
    }
}
