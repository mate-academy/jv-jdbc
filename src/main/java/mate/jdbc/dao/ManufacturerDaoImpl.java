package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.connection.ConnectionUtil;
import mate.jdbc.exception.RecordDoesNotExistException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertQuery = "INSERT INTO manufacturers (manufacturer_name, manufacturer_country)"
                + " VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufactureStatement = connection
                        .prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            createManufactureStatement.setString(1, manufacturer.getName());
            createManufactureStatement.setString(2, manufacturer.getCountry());
            createManufactureStatement.executeUpdate();
            ResultSet generatedKeys = createManufactureStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't insert new manufacture to DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String selectQuery = "SELECT * FROM manufacturers WHERE manufacturer_id = ? "
                + "AND is_deleted <> true;";
        Manufacturer manufacturer = new Manufacturer();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturer = connection.prepareStatement(selectQuery)) {
            if (isRecordExistInDB(id) > 0) {
                getManufacturer.setLong(1, id);
                ResultSet resultSet = getManufacturer
                        .executeQuery();
                if (!resultSet.isBeforeFirst()) {
                    return Optional.empty();
                } else {
                    while (resultSet.next()) {
                        manufacturer = getResultSet(resultSet);
                    }
                }
            } else {
                throw new RecordDoesNotExistException(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't execute get", e);
        }
        return Optional.of(manufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturerList = new ArrayList<>();
        String selectQuery = "SELECT * FROM manufacturers WHERE is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllManufacturers = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturers
                    .executeQuery(selectQuery);
            while (resultSet.next()) {
                Manufacturer newManufacturer = getResultSet(resultSet);
                manufacturerList.add(newManufacturer);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't execute get all", e);
        }
        return manufacturerList;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateQuery = "UPDATE manufacturers SET manufacturer_name = ?, "
                + "manufacturer_country = ? WHERE manufacturer_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            if (isRecordExistInDB(manufacturer.getId()) > 0) {
                updateStatement.setLong(3, manufacturer.getId());
                updateStatement.setString(1, manufacturer.getName());
                updateStatement.setString(2, manufacturer.getCountry());
                updateStatement.executeUpdate();
            } else {
                throw new RecordDoesNotExistException(manufacturer.getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't update record with id: "
                    + manufacturer.getId(), e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true "
                + "WHERE manufacturer_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturer = connection.prepareStatement(deleteRequest)) {
            if (isRecordExistInDB(id) > 0) {
                deleteManufacturer.setLong(1, id);
                return deleteManufacturer.executeUpdate() >= 1;
            } else {
                throw new RecordDoesNotExistException(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete record with id: " + id, e);
        }
    }

    private int isRecordExistInDB(Long id) {
        List<Manufacturer> allRecords = getAllEvenDeleted();
        int count = 0;
        for (Manufacturer record : allRecords) {
            if (record.getId().equals(id)) {
                count++;
                break;
            }
        }
        return count;
    }

    private List<Manufacturer> getAllEvenDeleted() {
        List<Manufacturer> allList = new ArrayList<>();
        String selectQuery = "SELECT * FROM manufacturers";
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllManufacturers = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturers.executeQuery(selectQuery);
            while (resultSet.next()) {
                Manufacturer newManufacturer = getResultSet(resultSet);
                allList.add(newManufacturer);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't execute get all even deleted", e);
        }
        return allList;
    }

    private Manufacturer getResultSet(ResultSet resultSet) {
        try {
            Long id = resultSet.getObject("manufacturer_id", Long.class);
            String name = resultSet.getString("manufacturer_name");
            String country = resultSet.getString("manufacturer_country");
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setId(id);
            manufacturer.setName(name);
            manufacturer.setCountry(country);
            return manufacturer;
        } catch (SQLException e) {
            throw new RuntimeException("Can't execute get all", e);
        }
    }
}
