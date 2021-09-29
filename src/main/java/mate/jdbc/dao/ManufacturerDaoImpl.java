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
    private static final int MANUFACTURER_ID = 1;
    private static final int MANUFACTURER_NAME = 2;
    private static final int MANUFACTURER_COUNTRY = 3;
    private static final int FIRST_PARAMETER_INDEX = 1;
    private static final int SECOND_PARAMETER_INDEX = 2;
    private static final int THIRD_PARAMETER_INDEX = 3;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String sqlRequest = "INSERT INTO manufacturers "
                + "(manufacturers_name, manufacturers_country) VALUES (?, ?);";
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement manufacturerCreateStatement =
                        connection.prepareStatement(sqlRequest, Statement.RETURN_GENERATED_KEYS)
        ) {
            manufacturerCreateStatement
                    .setString(FIRST_PARAMETER_INDEX, manufacturer.getName());
            manufacturerCreateStatement
                    .setString(SECOND_PARAMETER_INDEX, manufacturer.getCountry());
            manufacturerCreateStatement.executeUpdate();
            ResultSet resultSetUpdate = manufacturerCreateStatement.getGeneratedKeys();
            while (resultSetUpdate.next()) {
                Long id = resultSetUpdate.getObject(MANUFACTURER_ID, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't creat manufacturer " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Manufacturer manufacturer = null;
        String sqlRequest = "SELECT id, manufacturers_name, manufacturers_country "
                + "FROM manufacturers WHERE id = ? AND is_deleted = false;";
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement manufacturersGetStatement =
                        connection.prepareStatement(sqlRequest)
        ) {
            manufacturersGetStatement.setObject(FIRST_PARAMETER_INDEX, id);
            ResultSet resultSetById = manufacturersGetStatement.executeQuery();

            while (resultSetById.next()) {
                manufacturer = getDataFromResultSet(resultSetById);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by id, with id:" + id, e);
        }
        return Optional.ofNullable(manufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturerList = new ArrayList<>();
        String sqlRequest = "SELECT id, manufacturers_name, manufacturers_country "
                + "FROM manufacturers WHERE is_deleted = false;";
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement manufacturersGetAllStatement =
                        connection.prepareStatement(sqlRequest)
        ) {
            ResultSet resultSetFromDb = manufacturersGetAllStatement.executeQuery();
            while (resultSetFromDb.next()) {
                Manufacturer manufacturer = getDataFromResultSet(resultSetFromDb);;
                manufacturerList.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturer", e);
        }
        return manufacturerList;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String sqlRequest = "UPDATE manufacturers "
                + "SET manufacturers_name = ?, manufacturers_country = ? "
                + "WHERE id = ? AND is_deleted = false;";
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement manufacturerUpdateStatement =
                        connection.prepareStatement(sqlRequest)
        ) {
            manufacturerUpdateStatement
                    .setString(FIRST_PARAMETER_INDEX, manufacturer.getName());
            manufacturerUpdateStatement
                    .setString(SECOND_PARAMETER_INDEX, manufacturer.getCountry());
            manufacturerUpdateStatement
                    .setObject(THIRD_PARAMETER_INDEX, manufacturer.getId());
            manufacturerUpdateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't do the update " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String sqlRequest = "UPDATE manufacturers "
                + "SET is_deleted = true WHERE id = ?;";
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement manufacturerDeleteStatement =
                        connection.prepareStatement(sqlRequest)
        ) {
            manufacturerDeleteStatement.setObject(FIRST_PARAMETER_INDEX, id);
            return manufacturerDeleteStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer with id: " + id, e);
        }
    }

    private Manufacturer getDataFromResultSet(ResultSet resultSet) {
        try {
            return new Manufacturer(resultSet.getObject(MANUFACTURER_ID, Long.class),
                    resultSet.getString(MANUFACTURER_NAME),
                    resultSet.getString(MANUFACTURER_COUNTRY));
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t extract data from result set "
                    + resultSet, e);
        }
    }
}
