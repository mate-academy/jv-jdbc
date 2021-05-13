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
    public Manufacturer create(Manufacturer manufacturer) {
        String insertCreateRequest = "INSERT INTO manufacturers(name,country) values(?, ?)";
        try (Connection connectionToDB = ConnectionUtil.getConnection();
                PreparedStatement dbStatement
                        = connectionToDB.prepareStatement(insertCreateRequest,
                        Statement.RETURN_GENERATED_KEYS)) {
            dbStatement.setString(1,manufacturer.getName());
            dbStatement.setString(2,manufacturer.getCountry());
            dbStatement.executeUpdate();
            ResultSet generatedKeys = dbStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't create manufacturer: "
                    + manufacturer.toString() + " to DB", throwables);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String test = "SELECT * FROM manufacturers "
                + "WHERE id = ? AND is_deleted = false";
        try (Connection connectionToDB = ConnectionUtil.getConnection();
                PreparedStatement dbStatement = connectionToDB.prepareStatement(test)) {
            dbStatement.setLong(1,id);
            ResultSet resultSet = dbStatement.executeQuery();
            Manufacturer manufacturer = null;
            if (resultSet.next()) {
                manufacturer = getManufacturer(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't get manufacturer by id: " + id, throwables);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allFormats = new ArrayList<>();

        try (Connection connectionToDB = ConnectionUtil.getConnection();
                Statement dbStatement = connectionToDB.createStatement()) {
            ResultSet resultSet = dbStatement
                    .executeQuery("SELECT * FROM manufacturers "
                            + "WHERE is_deleted = false");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Long id = resultSet.getObject("id", Long.class);
                Manufacturer manufacturer = getManufacturer(resultSet);
                allFormats.add(manufacturer);
            }
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't get all data from DB", throwables);
        }
        return allFormats;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String insertUpdateRequest = "UPDATE manufacturers SET name = ?"
                + ",country = ? WHERE id = ? AND is_deleted = false";
        try (Connection connectionToDB = ConnectionUtil.getConnection();
                PreparedStatement dbStatement
                        = connectionToDB.prepareStatement(insertUpdateRequest,
                        Statement.RETURN_GENERATED_KEYS)) {
            dbStatement.setString(1,manufacturer.getName());
            dbStatement.setString(2,manufacturer.getCountry());
            dbStatement.setLong(3,manufacturer.getId());
            dbStatement.executeUpdate();
            ResultSet generatedKeys = dbStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't modify data: "
                    + manufacturer, throwables);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
        try (Connection connectionToDB = ConnectionUtil.getConnection();
                PreparedStatement dbStatement
                        = connectionToDB.prepareStatement(deleteRequest)) {
            dbStatement.setLong(1,id);
            return dbStatement.executeUpdate() >= 1;
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't delete data by id: "
                    + id + " from DB", throwables);
        }
    }

    private static Manufacturer getManufacturer(ResultSet resultSet) throws SQLException{
       Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getObject("id", Long.class));
        manufacturer.setName(resultSet.getString("name"));
        manufacturer.setCountry(resultSet.getString("country"));
        return manufacturer;
    }
}
