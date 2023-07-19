package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;
import mate.jdbc.util.DataProcessingException;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    public static final int NAME_PARAMETER_INDEX = 1;
    public static final int COUNTRY_PARAMETER_INDEX = 2;
    public static final int ID_PARAMETER_INDEX = 3;
    public static final int ID_COLUMN_INDEX = 1;
    private static final String CREATE_QUERY =
            "INSERT INTO manufacturers (name, country) VALUES (?, ?)";
    private static final String GET_QUERY = "SELECT * FROM manufacturers WHERE id = ?";
    private static final String GET_ALL_QUERY = "SELECT * FROM manufacturers";
    private static final String UPDATE_QUERY =
            "UPDATE manufacturers SET name = ?, country = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM manufacturers WHERE id = ?";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(NAME_PARAMETER_INDEX, manufacturer.getName());
            statement.setString(COUNTRY_PARAMETER_INDEX, manufacturer.getCountry());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                manufacturer.setId(generatedKeys.getLong(ID_COLUMN_INDEX));
                return manufacturer;
            } else {
                throw new SQLException("Failed to create manufacturer, no ID obtained.");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Error while creating manufacturer", e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        try (Connection connection = new ConnectionUtil().getConnection();
                PreparedStatement statement = connection.prepareStatement(GET_QUERY)) {
            statement.setLong(ID_COLUMN_INDEX, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(resultSet.getLong("id"));
                manufacturer.setName(resultSet.getString("name"));
                manufacturer.setCountry(resultSet.getString("country"));
                return Optional.of(manufacturer);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Error while getting manufacturer by ID", e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(GET_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            List<Manufacturer> manufacturers = new ArrayList<>();
            while (resultSet.next()) {
                String manufacturerName = resultSet.getString("name");
                String manufacturerCountry = resultSet.getString("country");
                Long manufacturerId = resultSet.getObject("id", Long.class);
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(manufacturerId);
                manufacturer.setName(manufacturerName);
                manufacturer.setCountry(manufacturerCountry);
                manufacturers.add(manufacturer);
            }
            return manufacturers;
        } catch (SQLException e) {
            throw new DataProcessingException("Error while getting all manufacturers", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(NAME_PARAMETER_INDEX, manufacturer.getName());
            statement.setString(COUNTRY_PARAMETER_INDEX, manufacturer.getCountry());
            statement.setLong(ID_PARAMETER_INDEX, manufacturer.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Failed to update manufacturer, no rows affected.");
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Error while updating manufacturer", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(ID_COLUMN_INDEX, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Error while deleting manufacturer", e);
        }
    }
}
