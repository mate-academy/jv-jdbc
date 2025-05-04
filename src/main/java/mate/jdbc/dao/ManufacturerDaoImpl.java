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
        String createRequest = "INSERT INTO manufacturers(name,country) values(?, ?)";
        try (Connection connectionToDB = ConnectionUtil.getConnection();
                PreparedStatement dbStatement
                        = connectionToDB.prepareStatement(createRequest,
                        Statement.RETURN_GENERATED_KEYS)) {
            dbStatement.setString(1,manufacturer.getName());
            dbStatement.setString(2,manufacturer.getCountry());
            dbStatement.executeUpdate();
            ResultSet generatedKeys = dbStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException throwable) {
            throw new DataProcessingException("Can't create manufacturer: "
                    + manufacturer.toString() + " to DB", throwable);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getRequest = "SELECT * FROM manufacturers "
                + "WHERE id = ? AND is_deleted = false";
        try (Connection connectionToDB = ConnectionUtil.getConnection();
                PreparedStatement dbStatement = connectionToDB.prepareStatement(getRequest)) {
            dbStatement.setLong(1,id);
            ResultSet resultSet = dbStatement.executeQuery();
            Manufacturer manufacturer = null;
            if (resultSet.next()) {
                manufacturer = getManufacturer(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException throwable) {
            throw new DataProcessingException("Can't get manufacturer by id: " + id, throwable);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManefacturers = new ArrayList<>();
        String getAllRequest = "SELECT * FROM manufacturers "
                + "WHERE is_deleted = false";
        try (Connection connectionToDB = ConnectionUtil.getConnection();
                Statement dbStatement = connectionToDB.createStatement()) {
            ResultSet resultSet = dbStatement
                    .executeQuery(getAllRequest);
            while (resultSet.next()) {
                Manufacturer manufacturer = getManufacturer(resultSet);
                allManefacturers.add(manufacturer);
            }
        } catch (SQLException throwable) {
            throw new DataProcessingException("Can't get all data from DB", throwable);
        }
        return allManefacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET name = ?"
                + ",country = ? WHERE id = ? AND is_deleted = false";
        try (Connection connectionToDB = ConnectionUtil.getConnection();
                PreparedStatement dbStatement
                        = connectionToDB.prepareStatement(updateRequest)) {
            dbStatement.setString(1,manufacturer.getName());
            dbStatement.setString(2,manufacturer.getCountry());
            dbStatement.setLong(3,manufacturer.getId());
            dbStatement.executeUpdate();
        } catch (SQLException throwable) {
            throw new DataProcessingException("Can't modify data: "
                    + manufacturer, throwable);
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
            return dbStatement.executeUpdate() > 0;
        } catch (SQLException throwable) {
            throw new DataProcessingException("Can't delete data by id: "
                    + id + " from DB", throwable);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getObject("id", Long.class));
        manufacturer.setName(resultSet.getString("name"));
        manufacturer.setCountry(resultSet.getString("country"));
        return manufacturer;
    }
}
