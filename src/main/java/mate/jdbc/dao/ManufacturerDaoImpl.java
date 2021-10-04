package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.exceptions.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    public static final int GENERATED_KEY_INDEX = 1;
    public static final int STATEMENT_FIRST_PARAM_INDEX = 1;
    public static final int STATEMENT_SECOND_PARAM_INDEX = 2;
    public static final int STATEMENT_THIRD_PARAM_INDEX = 3;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertQuery = "INSERT INTO manufacturers (name, country) VALUES (?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createStatement = connection
                        .prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            createStatement.setString(STATEMENT_FIRST_PARAM_INDEX, manufacturer.getName());
            createStatement.setString(STATEMENT_SECOND_PARAM_INDEX, manufacturer.getCountry());
            createStatement.executeUpdate();
            ResultSet generatedKeys = createStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(GENERATED_KEY_INDEX, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer into DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getQuery = "SELECT * FROM manufacturers "
                + "WHERE is_deleted = false AND id = (?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getStatement = connection
                        .prepareStatement(getQuery)) {
            getStatement.setLong(STATEMENT_FIRST_PARAM_INDEX, id);
            ResultSet resultSet = getStatement.executeQuery();
            Manufacturer manufacturer = new Manufacturer();
            if (resultSet.next()) {
                manufacturer = getDataFromResultSet(resultSet);
            }
            return Optional.of(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by id from DB: " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allCarManufacturers = new ArrayList<>();
        String getAllQuery = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllStatement = connection
                        .prepareStatement(getAllQuery)) {
            ResultSet resultSet = getAllStatement.executeQuery(getAllQuery);
            while (resultSet.next()) {
                allCarManufacturers.add(getDataFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturers from DB", e);
        }
        return allCarManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateQuery = "UPDATE manufacturers "
                + "SET name = (?), country = (?) WHERE id=(?) AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement = connection
                        .prepareStatement(updateQuery)) {
            updateStatement.setString(STATEMENT_FIRST_PARAM_INDEX, manufacturer.getName());
            updateStatement.setString(STATEMENT_SECOND_PARAM_INDEX, manufacturer.getCountry());
            updateStatement.setLong(STATEMENT_THIRD_PARAM_INDEX, manufacturer.getId());
            updateStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update given manufacturer ", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteQuery = "UPDATE manufacturers SET is_deleted = true WHERE id=(?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteStatement = connection
                        .prepareStatement(deleteQuery)) {
            deleteStatement.setLong(1, id);
            return deleteStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer by id from DB : " + id, e);
        }
    }

    private Manufacturer getDataFromResultSet(ResultSet resultSet) {
        try {
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setId(resultSet.getObject("id", Long.class));
            manufacturer.setName(resultSet.getString("name"));
            manufacturer.setCountry(resultSet.getString("country"));
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer data from DB", e);
        }
    }
}
