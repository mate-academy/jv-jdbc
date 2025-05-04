package mate.jdbc.manufacturer.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.castomexeption.DataProcessingException;
import mate.jdbc.connection.ConnectionUtil;
import mate.jdbc.manufacturer.ManufacturerDao;
import mate.jdbc.model.Manufacturer;

public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String COUNTRY = "country";
    private static final String CREATE_QUERY =
            "INSERT INTO manufacturers (name, country) VALUES (?, ?)";
    private static final String GET_QUERY =
            "SELECT * FROM manufacturers WHERE id = ?";
    private static final String GET_ALL_QUERY =
            "SELECT * FROM manufacturers";
    private static final String UPDATE_QUERY =
            "UPDATE manufacturers SET name = ?, country = ? WHERE id = ?";
    private static final String DELETE_QUERY =
            "DELETE FROM manufacturers WHERE id = ?";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
                         PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                manufacturer.setId(resultSet.getLong(1));
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to creat manufacturer: " + manufacturer, e);
        }
    }

    @Override
    public Optional<Manufacturer> get(long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(GET_QUERY)) {
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(resultSet.getLong(ID));
                manufacturer.setName(resultSet.getString(NAME));
                manufacturer.setCountry(resultSet.getString(COUNTRY));
                return Optional.of(manufacturer);
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get manufacturer with ID: " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(GET_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            List<Manufacturer> manufacturers = new ArrayList<>();
            while (resultSet.next()) {
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(resultSet.getLong(ID));
                manufacturer.setName(resultSet.getString(NAME));
                manufacturer.setCountry(resultSet.getString(COUNTRY));
                manufacturers.add(manufacturer);
            }
            return manufacturers;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get all manufacturers", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.setLong(3, manufacturer.getId());
            statement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to update manufacturer: " + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1,id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to delete manufacturer with ID: " + id, e);
        }
    }
}
