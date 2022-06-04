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
        String insertManufacturerCountryRequest =
                "INSERT INTO manufacturers(name,country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createFormatsStatement =
                        connection.prepareStatement(
                                     insertManufacturerCountryRequest,
                             Statement.RETURN_GENERATED_KEYS)) {
            createFormatsStatement.setString(1, manufacturer.getName());
            createFormatsStatement.setString(2, manufacturer.getCountry());
            createFormatsStatement.executeUpdate();
            ResultSet generatedKeys = createFormatsStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t create new manufacturer in DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturer = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement = connection
                        .prepareStatement(getManufacturer)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getManufacturer(resultSet));
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can`t get manufacturer with id:" + id, throwables);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getManufacturerStatement =
                        connection.createStatement()) {
            ResultSet resultSet =
                    getManufacturerStatement.executeQuery(
                            "SELECT * FROM manufacturers is_deleted = FALSE");
            while (resultSet.next()) {
                allManufacturers.add(getManufacturer(resultSet));
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can`t get all manufacturers", throwables);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturer SET name = ?, country = ? "
                + "WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createUpdateStatement =
                        connection.prepareStatement(updateRequest)) {
            createUpdateStatement.setString(1, manufacturer.getName());
            createUpdateStatement.setString(2, manufacturer.getCountry());
            createUpdateStatement.setLong(3, manufacturer.getId());
        } catch (SQLException throwables) {
            throw new DataProcessingException("It`s impossible to update manufacturer", throwables);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturer SET is_deleted = TRUE where id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createFormatsStatement =
                        connection.prepareStatement(deleteRequest,
                                Statement.RETURN_GENERATED_KEYS)) {
            createFormatsStatement.setLong(1, id);
            return createFormatsStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete manufacturer with id: " + id, e);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) {
        Long id = null;
        String name = null;
        String country = null;
        try {
            id = resultSet.getObject("id", Long.class);
            name = resultSet.getString("name");
            country = resultSet.getString("country");
        } catch (SQLException throwables) {
            throw new DataProcessingException("You can`t get manufacturers with current"
                    + " parameters ", throwables);
        }
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(id);
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        return manufacturer;
    }
}
