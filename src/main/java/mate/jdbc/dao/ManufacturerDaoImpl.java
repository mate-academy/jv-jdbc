package mate.jdbc.dao;

import mate.jdbc.exeption.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
private static final String TABLE_MANUFACTURERS = "manufacturers";
private static final String COLUMN_ID = "id";
private static final String COLUMN_NAME = "name";
private static final String COLUMN_COUNTRY = "country";
private static final String COLUMN_IS_DELETED = "is_deleted";
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertFormatRequest = "INSERT INTO " + TABLE_MANUFACTURERS + " (" +
                columnList(COLUMN_NAME, COLUMN_COUNTRY) + ") value(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement createManufacturerStatement
                     = connection.prepareStatement(insertFormatRequest, Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();

            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException throwables) {
            throw  new RuntimeException("Can not manufacturer to DB. Manufacturer: " + manufacturer, throwables);
        }
        return manufacturer;
    }

    @Override
        public Optional<Manufacturer> get(Long id) {
            String request = "SELECT * FROM " + TABLE_MANUFACTURERS + " WHERE "
                    + COLUMN_IS_DELETED + " = false AND id = ?";
            try (Connection connection = ConnectionUtil.getConnection();
            PreparedStatement getManufacture = connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS)) {
                getManufacture.setLong(1, id);
                ResultSet resultSet = getManufacture.executeQuery();
                if (resultSet.next()) {
                    return Optional.of(getManufacturerFromResultSet(resultSet));
                }
                return Optional.empty();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    @Override
    public List<Manufacturer> getAll() {
        String selectAllQuery = "SELECT * FROM " + TABLE_MANUFACTURERS + " WHERE "
                + COLUMN_IS_DELETED + " = 1";
        List<Manufacturer> allManufacturer = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
        PreparedStatement getAllManufacturerStatement = connection
                .prepareStatement(selectAllQuery, Statement.RETURN_GENERATED_KEYS)) {
            ResultSet resultSet = getAllManufacturerStatement.executeQuery();
            while (resultSet.next()) {
                allManufacturer.add(getManufacturerFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can not get all manufacturer from DB ", e);
        }
        return allManufacturer;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateQuery = "UPDATE " + TABLE_MANUFACTURERS + " SET " + COLUMN_NAME
                + " = ?, " + COLUMN_COUNTRY + " = ? WHERE " + COLUMN_ID + " = ?";
        try (Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.setLong(3, manufacturer.getId());
            statement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new RuntimeException("Can not update manufacturer: " + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteQuery = "UPDATE " + TABLE_MANUFACTURERS + " SET " + COLUMN_IS_DELETED
                + " = true WHERE " + COLUMN_ID + " = ?";
        try (Connection connection = ConnectionUtil.getConnection();
        PreparedStatement deletedStatement = connection.prepareStatement(deleteQuery)) {
            deletedStatement.setLong(1, id);
            return deletedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not update manufacturer from DB by id: " + id, e);
        }
    }

    private Manufacturer getManufacturerFromResultSet(ResultSet resultSet) {
        Manufacturer manufacturer = new Manufacturer();
        try {
            manufacturer.setId(resultSet.getObject(COLUMN_ID, Long.class));
            manufacturer.setName(resultSet.getString(COLUMN_NAME));
            manufacturer.setCountry(resultSet.getString(COLUMN_COUNTRY));
        } catch (SQLException e) {
            throw new RuntimeException("Can not get manufacturer from " + resultSet, e);
        }
        return manufacturer;
    }

    private String columnList(String ... values) {
        return String.join(",", values);
    }
}

