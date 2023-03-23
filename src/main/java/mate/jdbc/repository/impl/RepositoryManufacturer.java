package mate.jdbc.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.entity.Manufacturer;
import mate.jdbc.lib.Dao;
import mate.jdbc.lib.exception.DataProcessingException;
import mate.jdbc.repository.ConnectionSupplier;
import mate.jdbc.repository.Repository;

@Dao
public class RepositoryManufacturer implements Repository<Manufacturer> {

    public static final String CONNECTION_EXCEPTION = "Exception while getting connection for ";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_COUNTRY = "country";
    public static final String GET_FAILED = "Failed to obtain manufacturer with id: ";
    public static final String CREATE_FAILED = "Failed to create new manufacturer with name= ";
    public static final String UPDATE_FAILED = "Failed to update manufacturer with id: ";
    public static final String DELETE_FAILED = "Failed to delete manufacturer with id: ";
    public static final String GET_ALL_FAILED = "Failed to read all manufacturers from db.";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try (Connection connection = ConnectionSupplier.getConnection();
                PreparedStatement insertStatement = connection.prepareStatement(
                        "INSERT "
                                + "INTO manufacturers(" + COLUMN_NAME + "," + COLUMN_COUNTRY + ") "
                                + "Values(?,?);", Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setString(1, manufacturer.getName());
            insertStatement.setString(2, manufacturer.getCountry());
            insertStatement.executeUpdate();
            ResultSet resultSet = insertStatement.getGeneratedKeys();
            if (resultSet.next()) {
                manufacturer.setId(resultSet.getObject(1, Long.class));
                return manufacturer;
            } else {
                throw new DataProcessingException(GET_FAILED);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(CREATE_FAILED + manufacturer.getName(), e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        try (Connection connection = ConnectionSupplier.getConnection();
                PreparedStatement insertStatement = connection.prepareStatement(
                        "SELECT * "
                                + "FROM manufacturers "
                                + "WHERE id = ? "
                                + "AND is_deleted IS FALSE;")) {
            insertStatement.setLong(1, id);
            insertStatement.executeQuery();
            return parseResult(insertStatement.getResultSet()).stream().findFirst();
        } catch (SQLException e) {
            throw new DataProcessingException(GET_FAILED + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        try (Connection connection = ConnectionSupplier.getConnection();
                Statement getAllStatement = connection.createStatement()) {
            ResultSet resultSet = getAllStatement.executeQuery(
                    "SELECT * "
                            + "FROM manufacturers "
                            + "WHERE is_deleted IS FALSE;");
            return parseResult(resultSet);
        } catch (SQLException e) {
            throw new DataProcessingException(GET_ALL_FAILED, e);
        }
    }

    @Override
    public Optional<Manufacturer> update(Manufacturer manufacturer) {
        try (Connection connection = ConnectionSupplier.getConnection();
                PreparedStatement insertStatement = connection.prepareStatement(
                        "UPDATE manufacturers "
                                + "SET " + COLUMN_NAME + " = ?, " + COLUMN_COUNTRY + " = ? "
                                + "WHERE id = ? " + "AND is_deleted IS FALSE;")) {
            insertStatement.setString(1, manufacturer.getName());
            insertStatement.setString(2, manufacturer.getCountry());
            insertStatement.setLong(3, manufacturer.getId());
            insertStatement.executeUpdate();
            return get(manufacturer.getId());
        } catch (SQLException e) {
            throw new DataProcessingException(UPDATE_FAILED + manufacturer.getId(), e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionSupplier.getConnection();
                PreparedStatement insertStatement = connection.prepareStatement(
                        "UPDATE manufacturers "
                                + "SET is_deleted = true "
                                + "WHERE id = ? "
                                + "AND is_deleted IS FALSE;")) {
            insertStatement.setLong(1, id);
            return insertStatement.execute();
        } catch (SQLException e) {
            throw new DataProcessingException(DELETE_FAILED, e);
        }
    }

    private List<Manufacturer> parseResult(ResultSet resultSet) throws SQLException {
        List<Manufacturer> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(new Manufacturer(
                    resultSet.getObject(COLUMN_ID, Long.class),
                    resultSet.getObject(COLUMN_NAME, String.class),
                    resultSet.getObject(COLUMN_COUNTRY, String.class)));
        }
        return result;
    }
}
