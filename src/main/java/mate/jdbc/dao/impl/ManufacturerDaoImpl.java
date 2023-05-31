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
import mate.jdbc.exceptions.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest = "INSERT INTO manufacturers(name, country) VALUES(?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement =
                        connection.prepareStatement(insertManufacturerRequest,
                                Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer "
                    + manufacturer + " to DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturerRequest =
                "SELECT * from manufacturers WHERE is_deleted = false AND id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getManufacturerStatement =
                        connection.prepareStatement(getManufacturerRequest)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet generatedKeys = getManufacturerStatement.executeQuery();
            ;
            if (generatedKeys.next()) {
                Manufacturer gotManufacturer = getManufacturerInstance(id, generatedKeys);
                return Optional.of(gotManufacturer);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer with index "
                    + id + " from DB", e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String getAllManufacturerRequest =
                "SELECT * from manufacturers WHERE is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllManufacturerStatement = connection.createStatement()) {
            ResultSet allSet = getAllManufacturerStatement.executeQuery(getAllManufacturerRequest);
            while (allSet.next()) {
                Manufacturer pulledManufacturer = getFullManufacturedInstance(allSet);
                allManufacturers.add(pulledManufacturer);
            }
            return allManufacturers;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerRequest =
                "UPDATE manufacturers SET name = ?, country = ? "
                + "WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement = connection.prepareStatement(
                         updateManufacturerRequest)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer "
                    + manufacturer + " in DB", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufacturerRequest =
                "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement deleteManufacturerStatement = connection.prepareStatement(
                            deleteManufacturerRequest)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer with id "
                    + id + " in DB", e);
        }
    }

    private Manufacturer getManufacturerInstance(Long id, ResultSet generatedKeys)
            throws SQLException {
        String name = generatedKeys.getString(2);
        String country = generatedKeys.getString(3);
        return new Manufacturer(id, name, country);
    }

    private Manufacturer getFullManufacturedInstance(ResultSet allSet)
            throws SQLException {
        Long id = allSet.getObject("id", Long.class);
        String name = allSet.getString("name");
        String country = allSet.getString("country");
        return new Manufacturer(id, name, country);
    }
}
