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
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        final String insertRequest = "INSERT INTO manufacturers(name, country) value(?, ?);";
        try (PreparedStatement createStatement = preparedStatement(insertRequest)) {
            String name = manufacturer.getName();
            String country = manufacturer.getCountry();
            createStatement.setString(1, name);
            createStatement.setString(2, country);
            createStatement.executeUpdate();
            ResultSet generatedKeys = createStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer to DB " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getRequest = "SELECT id, name, country "
                + "FROM manufacturers "
                + "WHERE id = ? AND is_deleted = false;";
        try (PreparedStatement getManufacturerStatement = preparedStatement(getRequest)) {
            getManufacturerStatement.setLong(1, id);
            Manufacturer manufacturer = null;
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            if (resultSet.next()) {
                manufacturer = initiationManufacturer(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by ID " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllQuery = "SELECT * FROM manufacturers WHERE is_deleted = false";
        try (PreparedStatement getAllManufacturersStatement
                         = preparedStatement(getAllQuery)) {
            List<Manufacturer> allManufacturers = new ArrayList<>();
            ResultSet resultSet = getAllManufacturersStatement.executeQuery();
            while (resultSet.next()) {
                allManufacturers.add(initiationManufacturer(resultSet));
            }
            return allManufacturers;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String deleteRequest = "UPDATE manufacturers SET name = ?, country = ? WHERE id = ?;";
        try (PreparedStatement updateManufacturerStatement = preparedStatement(deleteRequest)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer " + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        final String deleteRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (PreparedStatement deleteManufacturerStatement = preparedStatement(deleteRequest)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer with ID " + id, e);
        }
    }

    private PreparedStatement preparedStatement(String request) throws SQLException {
        Connection connection = ConnectionUtil.getConnection();
        return connection
                .prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
    }

    private Manufacturer initiationManufacturer(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(id);
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        return manufacturer;
    }
}
