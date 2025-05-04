package mate.jdbc.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final String DATABASE_TABLE_NAME = "manufacturers";
    private static final int ID_INDEX = 1;
    private static final int NAME_INDEX = 2;
    private static final int COUNTRY_INDEX = 3;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String sqlQuery = "INSERT INTO " + DATABASE_TABLE_NAME
                + "(name, country) VALUES(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement insertStatement = connection.prepareStatement(sqlQuery,
                        Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setString(1, manufacturer.getName());
            insertStatement.setString(2, manufacturer.getCountry());
            insertStatement.executeUpdate();
            ResultSet generatedKeys = insertStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String sqlQuery = "SELECT * FROM " + DATABASE_TABLE_NAME
                + " WHERE id = ? AND is_deleted = FALSE;";
        Manufacturer manufacturer = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement selectStatement = connection.prepareStatement(sqlQuery)) {
            selectStatement.setLong(1, id);
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                manufacturer = processRow(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacture with id: " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> result = new ArrayList<>();
        String sqlQuery = "SELECT * FROM " + DATABASE_TABLE_NAME
                + " WHERE is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllStatement = connection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = getAllStatement.executeQuery();
            while (resultSet.next()) {
                Manufacturer manufacturer = processRow(resultSet);
                result.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers list", e);
        }
        return result;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String sqlQuery = "UPDATE " + DATABASE_TABLE_NAME + " SET name = ?,"
                + " country = ? WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement = connection.prepareStatement(sqlQuery)) {
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.setLong(3, manufacturer.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String sqlQuery = "DELETE FROM " + DATABASE_TABLE_NAME + " WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteStatement = connection.prepareStatement(sqlQuery)) {
            deleteStatement.setLong(1, id);
            int updatedRows = deleteStatement.executeUpdate();
            return updatedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer with id: " + id, e);
        }
    }

    private Manufacturer processRow(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getObject(ID_INDEX, Long.class));
        manufacturer.setName(resultSet.getString(NAME_INDEX));
        manufacturer.setCountry(resultSet.getString(COUNTRY_INDEX));
        return manufacturer;
    }
}
