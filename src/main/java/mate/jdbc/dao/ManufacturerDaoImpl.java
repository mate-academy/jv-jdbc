package mate.jdbc.dao;

import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private final StringBuilder stringBuilder = new StringBuilder();

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        if (manufacturer.getId() != null) {
            throw new DataProcessingException("Hardcoded id not allowed");
        }
        checkIfDuplicated(manufacturer);
        return insertByValue(manufacturer);
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        stringBuilder.delete(0, stringBuilder.length());
        String selectByIdString =
                stringBuilder.append("SELECT id, name, country ")
                        .append("FROM manufacturers ")
                        .append("WHERE id = ? AND is_deleted = false;")
                        .toString();
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement selectByIdStatement = connection
                        .prepareStatement(selectByIdString)) {
            selectByIdStatement.setLong(1, id);
            ResultSet resultSet = selectByIdStatement.executeQuery();
            Manufacturer manufacturer = null;
            if (resultSet.next()) {
                manufacturer = retrieveManufacturer(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't retrieve manufacturer by id: " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        stringBuilder.delete(0, stringBuilder.length());
        String selectAllString =
                stringBuilder.append("SELECT id, name, country ")
                        .append("FROM manufacturers ")
                        .append("WHERE is_deleted = false;")
                        .toString();
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement selectAllStatement = connection
                         .prepareStatement(selectAllString)) {
            ResultSet resultSet = selectAllStatement.executeQuery();
            while (resultSet.next()) {
                manufacturers.add(retrieveManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't retrieve manufacturers from db" + e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        if (manufacturer.getId() == null) {
            throw new DataProcessingException("Can't update record, id is null");
        }
        stringBuilder.delete(0, stringBuilder.length());
        String updateString =
                stringBuilder.append("UPDATE manufacturers ")
                        .append("SET name = ?, country = ?")
                        .append("WHERE id = ? AND is_deleted = FALSE;").toString();
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement updateStatement = connection.prepareStatement(updateString)) {
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.setLong(3, manufacturer.getId());
            if (updateStatement.executeUpdate() > 0) {
                return manufacturer;
            } else {
                throw new DataProcessingException("Can't update record: " + manufacturer);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        stringBuilder.delete(0, stringBuilder.length());
        String deleteByIdString =
                stringBuilder.append("UPDATE manufacturers ")
                        .append("SET is_deleted = ? ")
                        .append("WHERE id = ?;").toString();
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement deleteByIdStatement = connection
                         .prepareStatement(deleteByIdString)) {
            deleteByIdStatement.setBoolean(1, true);
            deleteByIdStatement.setLong(2, id);
            return deleteByIdStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Manufacturer retrieveManufacturer(ResultSet resultSet) {
        try {
            Long id = resultSet.getObject("id", Long.class);
            String name = resultSet.getString("name");
            String country = resultSet.getString("country");
            return new Manufacturer(id, name, country);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't read data from result set " + e);
        }
    }

    private void checkIfDuplicated(Manufacturer manufacturer) {
        stringBuilder.delete(0, stringBuilder.length());
        String selectStr =
                stringBuilder.append("SELECT * FROM manufacturers ")
                        .append("WHERE manufacturers.id IN ")
                        .append("(SELECT id FROM manufacturers AS m ")
                        .append("WHERE m.country = ? AND m.name = ? AND is_deleted = FALSE);")
                        .toString();
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement selectByIdStmt = connection.prepareStatement(selectStr)) {
            selectByIdStmt.setString(1, manufacturer.getCountry());
            selectByIdStmt.setString(2, manufacturer.getName());
            ResultSet resultSet = selectByIdStmt.executeQuery();
            if (resultSet.next()) {
                throw new DataProcessingException("Can't create new record: "
                        + manufacturer + " is already in table");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Manufacturer insertByValue(Manufacturer manufacturer) {
        stringBuilder.delete(0, stringBuilder.length());
        String insertStr = stringBuilder
                .append("INSERT INTO manufacturers (name, country) ")
                .append("VALUES (?, ?);").toString();
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement insertStatement = connection
                        .prepareStatement(insertStr, Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setString(1, manufacturer.getName());
            insertStatement.setString(2, manufacturer.getCountry());
            insertStatement.executeUpdate();
            ResultSet generatedKeys = insertStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert record "
                    + manufacturer + " to database" + e);
        }
        return manufacturer;
    }
}
