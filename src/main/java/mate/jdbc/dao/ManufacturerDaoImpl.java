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
        String insertRequest = "INSERT INTO manufacturers "
                + "(name, country) VALUES(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement insertStatement = connection.prepareStatement(insertRequest,
                        Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setString(1, manufacturer.getName());
            insertStatement.setString(2, manufacturer.getCountry());
            insertStatement.executeUpdate();
            ResultSet generatedKeys = insertStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                manufacturer.setId(generatedKeys.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Cant insert name: " + manufacturer.getName() + ", country: "
                            + manufacturer.getCountry() + " to db", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getRequest = "SELECT * FROM manufacturers WHERE id = ?"
                + " AND deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturer
                        = connection.prepareStatement(getRequest)) {
            getManufacturer.setLong(1, id);
            ResultSet resultSet = getManufacturer.executeQuery();
            Manufacturer manufacturer = null;
            if (resultSet.next()) {
                manufacturer = makeManufacturer(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Cant get manufacturer id: "
                    + id + " from the db", e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String selectRequest = "SELECT * FROM manufacturers WHERE deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllManufacturers = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturers
                    .executeQuery(selectRequest);
            while (resultSet.next()) {
                manufacturers.add(makeManufacturer(resultSet));
            }
            return manufacturers;
        } catch (SQLException e) {
            throw new DataProcessingException("Cant get all manufacturers from db", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET name = ?,"
                + " country = ? WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement updateManufacturer
                         = connection.prepareStatement(updateRequest)) {
            updateManufacturer.setString(1, manufacturer.getName());
            updateManufacturer.setString(2, manufacturer.getCountry());
            updateManufacturer.setObject(3, manufacturer.getId());
            updateManufacturer.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Cant update a manufacturer with id: "
                    + manufacturer.getId() + " in db", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET deleted = true WHERE id = ?" +
                " AND deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement deletedManufacturers
                         = connection.prepareStatement(deleteRequest)) {
            deletedManufacturers.setLong(1, id);
            return deletedManufacturers.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Cant delete a manufacturer with id: "
                    + id + " in db", e);
        }
    }

    private Manufacturer makeManufacturer(ResultSet resultSet) {
        try {
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String country = resultSet.getString("country");
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setId(id);
            manufacturer.setName(name);
            manufacturer.setCountry(country);
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Not relevant data to make manufacturer model", e);
        }
    }
}
