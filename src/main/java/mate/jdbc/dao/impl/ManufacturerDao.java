package mate.jdbc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.GenericDao;
import mate.jdbc.lib.Dao;
import mate.jdbc.lib.DataProcessingException;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionToDbUtil;

@Dao
public class ManufacturerDao implements GenericDao<Manufacturer> {
    private static final String NOT_DELETED = "is_deleted = " + false;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String query = "INSERT INTO manufacturers (name,country) VALUES (?, ?);";
        try (Connection connection = ConnectionToDbUtil.getConnection();
                PreparedStatement statement = connection
                            .prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer to DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String query = "SELECT * FROM manufacturers WHERE id = ?, " + NOT_DELETED + ";";
        try (Connection connection = ConnectionToDbUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer from DB", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        try (Connection connection = ConnectionToDbUtil.getConnection();
                 Statement statement = connection.createStatement()) {
            List<Manufacturer> manufacturers = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM manufacturers WHERE "
                    + NOT_DELETED + ";");
            while (resultSet.next()) {
                manufacturers.add(getManufacturer(resultSet));
            }
            return manufacturers;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all data from DB", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String query = "UPDATE manufacturers SET name = ?, country = ?"
                + " WHERE (id = ?) AND " + false + ";";
        try (Connection connection = ConnectionToDbUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.setLong(3, manufacturer.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer in DB", e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE manufacturers SET is_deleted = "
                + true + " WHERE (id = " + id.toString() + ");";
        try (Connection connection = ConnectionToDbUtil.getConnection();
                Statement statement = connection.createStatement()) {
            if (statement.executeUpdate(query) > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer from DB", e);
        }
        return false;
    }

    private Manufacturer getManufacturer(ResultSet resultSet) {
        Manufacturer manufacturer = new Manufacturer();
        try {
            manufacturer.setId(resultSet.getObject("id", Long.class));
            manufacturer.setName(resultSet.getString("name"));
            manufacturer.setCountry(resultSet.getString("country"));
        } catch (SQLException e) {
            throw new RuntimeException("Error when receiving data from ResultSet", e);
        }
        return manufacturer;
    }
}
