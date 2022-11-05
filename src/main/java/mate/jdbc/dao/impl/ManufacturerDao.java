package mate.jdbc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import mate.jdbc.dao.Dao;
import mate.jdbc.lib.DataProcessingException;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionToDbUtil;
import mate.jdbc.util.DbPropertiesFileReader;

@mate.jdbc.lib.Dao
public class ManufacturerDao implements Dao<Manufacturer> {
    private static final int MYSQl_TRUE = 1;
    private static final int MYSQL_FALSE = 0;
    private static final String NOT_DELETED = "is_deleted = " + MYSQL_FALSE;
    private static final Properties properties = DbPropertiesFileReader
            .getPropertiesFrom("src/main/resources/DBProperties");

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String preparedRequest = "INSERT INTO manufacturers (name,country) VALUES (?, ?);";
        try (Connection connection = ConnectionToDbUtil.getConnection(properties);
                PreparedStatement statement = connection
                            .prepareStatement(preparedRequest, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Error with connection to DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String preparedRequest = "SELECT * FROM manufacturers WHERE id = ?, " + NOT_DELETED + ";";
        try (Connection connection = ConnectionToDbUtil.getConnection(properties);
                 PreparedStatement statement = connection.prepareStatement(preparedRequest)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Error with connection to DB", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        try (Connection connection = ConnectionToDbUtil.getConnection(properties);
                 Statement statement = connection.createStatement()) {
            List<Manufacturer> manufacturers = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM manufacturers WHERE "
                    + NOT_DELETED + ";");
            while (resultSet.next()) {
                manufacturers.add(getManufacturer(resultSet));
            }
            return manufacturers;
        } catch (SQLException e) {
            throw new DataProcessingException("Error with connection to DB", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String preparedRequest = "UPDATE manufacturers SET name = ?, country = ?"
                + " WHERE (id = ?), " + NOT_DELETED + ";";
        try (Connection connection = ConnectionToDbUtil.getConnection(properties);
                 PreparedStatement statement = connection.prepareStatement(preparedRequest)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.setLong(3, manufacturer.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Error with connection to DB", e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String preparedRequest = "UPDATE manufacturers SET is_deleted = "
                + MYSQl_TRUE + " WHERE (id = " + id.toString() + ");";
        try (Connection connection = ConnectionToDbUtil.getConnection(properties);
                Statement statement = connection.createStatement()) {
            if (statement.executeUpdate(preparedRequest) > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Error with connection to DB", e);
        }
        return false;
    }

    private Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getObject("id", Long.class));
        manufacturer.setName(resultSet.getString("name"));
        manufacturer.setCountry(resultSet.getString("country"));
        return manufacturer;
    }
}
