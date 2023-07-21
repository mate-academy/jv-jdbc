package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.exeption.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final String NAME_OF_TABLE = "manufacturers";
    private static final String TABLE_COLUMN_NAME = "name";
    private static final String TABLE_COLUMN_COUNTRY = "country";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String sqlRequest =
                "INSERT INTO " + NAME_OF_TABLE
                        + " (" + TABLE_COLUMN_NAME + ", " + TABLE_COLUMN_COUNTRY
                        + ") VALUES(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement pStatement =
                           connection.prepareStatement(sqlRequest,
                                  Statement.RETURN_GENERATED_KEYS)) {
            pStatement.setString(1, manufacturer.getName());
            pStatement.setString(2, manufacturer.getCountry());
            pStatement.executeUpdate();
            ResultSet generatedKey = pStatement.getGeneratedKeys();
            if (generatedKey.next()) {
                Long id = generatedKey.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException err) {
            throw new DataProcessingException(
                    "Can't INSERT " + NAME_OF_TABLE + " to DataBase", err);
        }
        return manufacturer;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        boolean isUpdated;
        String sqlRequest = "UPDATE " + NAME_OF_TABLE
                + " SET  name = ?, country = ? WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement pStatement =
                        connection.prepareStatement(sqlRequest)) {
            pStatement.setString(1, manufacturer.getName());
            pStatement.setString(2, manufacturer.getCountry());
            pStatement.setLong(3, manufacturer.getId());
            isUpdated = pStatement.executeUpdate() > 0;
        } catch (SQLException err) {
            throw new DataProcessingException(
                    "Can't execute UPDATE for record: " + manufacturer, err);
        }
        if (!isUpdated) {
            throw new DataProcessingException("Can't UPDATE " + manufacturer);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String sqlRequest = "SELECT * FROM " + NAME_OF_TABLE
                + " WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement pStatement =
                        connection.prepareStatement(sqlRequest)) {
            pStatement.setLong(1, id);
            ResultSet resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(parseRecord(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException err) {
            throw new DataProcessingException(
                    "Can't get record with id=" + id + " from " + NAME_OF_TABLE, err);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String sqlRequest = "SELECT * FROM " + NAME_OF_TABLE + " WHERE is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement pStatement =
                        connection.prepareStatement(sqlRequest)) {
            ResultSet resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                manufacturers.add(parseRecord(resultSet));
            }
        } catch (SQLException err) {
            throw new DataProcessingException(
                    "Can't get all " + NAME_OF_TABLE + " from DataBase", err);
        }
        return manufacturers;
    }

    @Override
    public boolean delete(Long id) {
        String sqlRequest = "UPDATE " + NAME_OF_TABLE
                + " SET is_deleted = true WHERE id = ? AND is_deleted = FALSE";
        boolean isExecuted;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement pStatement =
                        connection.prepareStatement(sqlRequest, Statement.RETURN_GENERATED_KEYS)) {
            pStatement.setLong(1, id);
            isExecuted = pStatement.executeUpdate() > 0;
            return isExecuted;
        } catch (SQLException err) {
            throw new DataProcessingException(
                    "Can't execute soft DELETE for record with id:" + id, err);
        }
    }

    private Manufacturer parseRecord(ResultSet resultSet) {
        try {
            String name = resultSet.getString("name");
            String country = resultSet.getString("country");
            Long id = resultSet.getObject("id", Long.class);
            return new Manufacturer(id, name, country);
        } catch (SQLException err) {
            throw new DataProcessingException(
                    "Can't get record from: " + NAME_OF_TABLE, err);
        }
    }
}
