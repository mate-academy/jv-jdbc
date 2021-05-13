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
        String createManufacturerRequest
                     = "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement createManufacturerStatement
                         = connection.prepareStatement(createManufacturerRequest,
                         Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1,
                    manufacturer.getName());
            createManufacturerStatement.setString(2,
                    manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement
                    .getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't create manufacturer"
                    + " and update to DB", throwables);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Manufacturer manufacturer = new Manufacturer();
        String getManufacturerRequest = "SELECT * FROM manufacturers "
                + "WHERE id = ? and is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement
                         = connection.prepareStatement(getManufacturerRequest)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement
                    .executeQuery();
            while (resultSet.next()) {
                manufacturer.setName(resultSet.getString("name"));
                manufacturer.setCountry(resultSet.getString("country"));
                manufacturer.setId(id);
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't get manufacturer from DB",
                    throwables);
        }
        return Optional.of(manufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String getAllManufacturersRequest = "SELECT * FROM manufacturers"
                + " WHERE is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllManufacturersStatement
                         = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturersStatement
                    .executeQuery(getAllManufacturersRequest);
            while (resultSet.next()) {
                String name
                        = resultSet.getString("name");
                String country
                        = resultSet.getString("country");
                Manufacturer manufacturer = new Manufacturer();
                Long id = resultSet.getObject("id", Long.class);
                manufacturer.setId(id);
                manufacturer.setName(name);
                manufacturer.setCountry(country);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't get manufacturers from DB",
                    throwables);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerRequest = "UPDATE manufacturers SET name = (?), "
                + "country = (?) WHERE id = (?) and is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getManufacturerStatement
                          = connection.prepareStatement(updateManufacturerRequest,
                         Statement.RETURN_GENERATED_KEYS)) {
            getManufacturerStatement
                    .setString(1, manufacturer.getName());
            getManufacturerStatement
                    .setString(2, manufacturer.getCountry());
            getManufacturerStatement
                    .setObject(3, manufacturer.getId());
            getManufacturerStatement
                    .executeUpdate();
            return manufacturer;
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't update Manufacturer in DB",
                    throwables);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufacturerRequest = "UPDATE manufacturers SET "
                + "is_deleted = true WHERE id =?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement
                         = connection.prepareStatement(deleteManufacturerRequest,
                        Statement.RETURN_GENERATED_KEYS)) {
            deleteManufacturerStatement.setLong(1,id);
            return deleteManufacturerStatement.executeUpdate() >= 1;
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't delete manufacturer from DB",
                    throwables);
        }
    }
}
