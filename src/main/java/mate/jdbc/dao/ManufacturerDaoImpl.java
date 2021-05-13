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
    private static final int FIRST_PARAMETER_INDEX = 1;
    private static final int SECOND_PARAMETER_INDEX = 2;
    private static final int THIRD_PARAMETER_INDEX = 3;

    @Override
    public void clearManufacturersTable() {
        try (Connection connection = ConnectionUtil.getConnection();
                Statement clearTableStatement = connection.createStatement()) {
            clearTableStatement.executeUpdate("TRUNCATE TABLE manufacturers;");
        } catch (SQLException e) {
            throw new DataProcessingException("Can't clear manufacturers table", e);
        }
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String query = "INSERT INTO manufacturers(name, country) VALUES(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createStatement =
                        connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            createStatement.setString(FIRST_PARAMETER_INDEX, manufacturer.getName());
            createStatement.setString(SECOND_PARAMETER_INDEX,
                    manufacturer.getCountry());
            createStatement.executeUpdate();
            ResultSet generatedKeys = createStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                manufacturer.setId(generatedKeys.getObject(1, Long.class));
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer to DB " + manufacturer, e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Manufacturer manufacturer = null;
        String query = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getByIdStatement = connection.prepareStatement(query)) {
            getByIdStatement.setLong(FIRST_PARAMETER_INDEX, id);
            ResultSet resultSet = getByIdStatement.executeQuery();
            if (resultSet.next()) {
                manufacturer = createManufacturerFromResultSet(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer from DB by id: " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String query = "SELECT * FROM manufacturers WHERE is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                 Statement getAllManufacturersStatement = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturersStatement.executeQuery(query);
            List<Manufacturer> allManufacturers = new ArrayList<>();
            while (resultSet.next()) {
                allManufacturers.add(createManufacturerFromResultSet(resultSet));
            }
            return allManufacturers;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB ", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String query = "UPDATE manufacturers SET name = ?, "
                + "country = ? WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement = connection.prepareStatement(query)) {
            updateStatement.setString(FIRST_PARAMETER_INDEX, manufacturer.getName());
            updateStatement.setString(SECOND_PARAMETER_INDEX, manufacturer.getCountry());
            updateStatement.setLong(THIRD_PARAMETER_INDEX, manufacturer.getId());
            if (updateStatement.executeUpdate() > 0) {
                return manufacturer;
            }
            throw new RuntimeException("There is no manufacturer with such id in DB: "
                    + manufacturer.getId());
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer: " + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement deleteStatement = connection.prepareStatement(query)) {
            deleteStatement.setLong(FIRST_PARAMETER_INDEX, id);
            return deleteStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer from DB by id: " + id, e);
        }
    }

    private Manufacturer createManufacturerFromResultSet(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getObject("id", Long.class));
        manufacturer.setName(resultSet.getString("name"));
        manufacturer.setCountry(resultSet.getString("country"));
        return manufacturer;
    }
}
