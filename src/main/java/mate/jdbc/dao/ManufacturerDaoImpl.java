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
    private static final int COLUMN_INDEX = 1;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertRequest = "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                   PreparedStatement insertManufacturerStatement =
                           connection.prepareStatement(insertRequest, Statement
                                   .RETURN_GENERATED_KEYS)) {
            insertManufacturerStatement.setString(FIRST_PARAMETER_INDEX, manufacturer.getName());
            insertManufacturerStatement
                    .setString(SECOND_PARAMETER_INDEX, manufacturer.getCountry());
            insertManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = insertManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(COLUMN_INDEX, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add manufacturer "
                    + manufacturer + " to database", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getByIdRequest = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false;";
        Manufacturer manufacturer = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getByIdStatement = connection.prepareStatement(getByIdRequest)) {
            getByIdStatement.setLong(1, id);
            ResultSet resultSet = getByIdStatement.executeQuery();
            while (resultSet.next()) {
                manufacturer = resultSetParsing(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find manufacturer with ID "
                    + id + " in database", e);
        }
        return Optional.ofNullable(manufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String readAllRequest = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllStatement =
                        connection.prepareStatement(readAllRequest)) {
            ResultSet resultSet = getAllStatement.executeQuery(readAllRequest);
            while (resultSet.next()) {
                allManufacturers.add(resultSetParsing(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET name = ?, country = ?"
                + " WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement =
                           connection.prepareStatement(updateRequest)) {
            updateManufacturerStatement
                    .setString(FIRST_PARAMETER_INDEX, manufacturer.getName());
            updateManufacturerStatement
                    .setString(SECOND_PARAMETER_INDEX, manufacturer.getCountry());
            updateManufacturerStatement
                    .setLong(THIRD_PARAMETER_INDEX, manufacturer.getId());
            int updateRows = updateManufacturerStatement.executeUpdate();
            if (updateRows == 0) {
                throw new DataProcessingException("There is no manufacturer with ID = "
                        + manufacturer.getId() + " in database to update.");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer = "
                    + manufacturer + " in database", e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement =
                           connection.prepareStatement(deleteRequest)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturers with ID = "
                    + id + " from DB", e);
        }
    }

    private Manufacturer resultSetParsing(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        manufacturer.setId(id);
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        return manufacturer;
    }
}
