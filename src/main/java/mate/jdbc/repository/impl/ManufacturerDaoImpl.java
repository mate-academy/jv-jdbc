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
import mate.jdbc.repository.GenericDao;

@Dao
public class ManufacturerDaoImpl implements GenericDao<Manufacturer> {

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_COUNTRY = "country";
    private static final String STATEMENT_UPDATE = "UPDATE manufacturers "
            + "SET " + COLUMN_NAME + " = ?, " + COLUMN_COUNTRY + " = ? "
            + "WHERE id = ? " + "AND is_deleted IS FALSE;";
    private static final String STATEMENT_CREATE = "INSERT "
            + "INTO manufacturers(" + COLUMN_NAME + "," + COLUMN_COUNTRY + ") "
            + "Values(?,?);";
    private static final String STATEMENT_GET = "SELECT * "
            + "FROM manufacturers "
            + "WHERE id = ? "
            + "AND is_deleted IS FALSE;";
    private static final String STATEMENT_GETALL = "SELECT * "
            + "FROM manufacturers "
            + "WHERE is_deleted IS FALSE;";
    private static final String STATEMENT_DELETE = "UPDATE manufacturers "
            + "SET is_deleted = true "
            + "WHERE id = ? "
            + "AND is_deleted IS FALSE;";
    private static final String GET_FAILED = "Failed to obtain manufacturer with id: ";
    private static final String CREATE_FAILED = "Failed to create new manufacturer with name= ";
    private static final String UPDATE_FAILED = "Failed to update manufacturer with id: ";
    private static final String DELETE_FAILED = "Failed to delete manufacturer with id: ";
    private static final String GET_ALL_FAILED = "Failed to read all manufacturers from db.";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try (Connection connection = ConnectionSupplier.getConnection();
                PreparedStatement createStatement = connection.prepareStatement(
                        STATEMENT_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            createStatement.setString(1, manufacturer.getName());
            createStatement.setString(2, manufacturer.getCountry());
            createStatement.executeUpdate();
            ResultSet resultSet = createStatement.getGeneratedKeys();
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
                PreparedStatement getStatement = connection.prepareStatement(
                        STATEMENT_GET)) {
            getStatement.setLong(1, id);
            getStatement.executeQuery();
            return parseResult(getStatement.getResultSet()).stream().findFirst();
        } catch (SQLException e) {
            throw new DataProcessingException(GET_FAILED + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        try (Connection connection = ConnectionSupplier.getConnection();
                Statement getAllStatement = connection.createStatement()) {
            ResultSet resultSet = getAllStatement.executeQuery(
                    STATEMENT_GETALL);
            return parseResult(resultSet);
        } catch (SQLException e) {
            throw new DataProcessingException(GET_ALL_FAILED, e);
        }
    }

    @Override
    public Optional<Manufacturer> update(Manufacturer manufacturer) {
        try (Connection connection = ConnectionSupplier.getConnection();
                PreparedStatement updateStatement = connection.prepareStatement(
                        STATEMENT_UPDATE)) {
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.setLong(3, manufacturer.getId());
            updateStatement.executeUpdate();
            return Optional.of(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException(UPDATE_FAILED + manufacturer.getId(), e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionSupplier.getConnection();
                PreparedStatement deleteStatement = connection.prepareStatement(
                        STATEMENT_DELETE)) {
            deleteStatement.setLong(1, id);
            return deleteStatement.executeUpdate() > 0;
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
