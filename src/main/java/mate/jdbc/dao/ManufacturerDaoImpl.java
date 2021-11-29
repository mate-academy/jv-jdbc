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
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertFormatRequest = "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement createManufacturerStatement = connection
                         .prepareStatement(insertFormatRequest, Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't insert manufacturers to DB", throwables);
        }
        return null;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getQuery = "SELECT * FROM manufacturers WHERE id = ?;";
        Manufacturer manufacturer = null;
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getQueryByIdStatement = connection.prepareStatement(getQuery)) {
            getQueryByIdStatement.setLong(1, id);
            ResultSet resultSet = getQueryByIdStatement.executeQuery();
            if (resultSet.next()) {
                manufacturer = new Manufacturer();
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                manufacturer.setId(id);
                manufacturer.setName(name);
                manufacturer.setCountry(country);
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't get manufacturers from DB", throwables);
        }
        return Optional.ofNullable(manufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String query = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllFromTableStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = getAllFromTableStatement.executeQuery(query);
            while (resultSet.next()) {
                Long id = resultSet.getObject("id", Long.class);
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(id);
                manufacturer.setName(name);
                manufacturer.setCountry(country);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't get manufacturers from DB", throwables);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET name = ?, country = ? WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturersStatement = connection
                        .prepareStatement(updateRequest, Statement.RETURN_GENERATED_KEYS)) {
            updateManufacturersStatement.setString(1, manufacturer.getName());
            updateManufacturersStatement.setString(2, manufacturer.getCountry());
            updateManufacturersStatement.setLong(3, manufacturer.getId());
            updateManufacturersStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't delete manufacturers", throwables);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturersStatement = connection
                        .prepareStatement(deleteRequest)) {
            deleteManufacturersStatement.setLong(1, id);
            return deleteManufacturersStatement.executeUpdate() >= 1;
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't delete manufacturers", throwables);
        }
    }
}
