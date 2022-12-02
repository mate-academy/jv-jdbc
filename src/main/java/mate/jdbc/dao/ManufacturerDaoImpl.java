package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.lib.Dao;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectToDB;
import mate.jdbc.util.ConnectToMySql;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private final ConnectToDB connectToDB;

    public ManufacturerDaoImpl() {
        connectToDB = new ConnectToMySql();
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String createQueryString = "INSERT INTO manufacturers(name, country) values(?, ?)";
        try (Connection connection = connectToDB.getConnection();
                PreparedStatement createRecordStatement =
                        connection.prepareStatement(createQueryString,
                                                    PreparedStatement.RETURN_GENERATED_KEYS)) {
            createRecordStatement.setString(1, manufacturer.getName());
            createRecordStatement.setString(2, manufacturer.getCountry());
            createRecordStatement.executeUpdate();
            ResultSet generatedKeys = createRecordStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                manufacturer.setId(generatedKeys.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can`t insert record to manufacturers table", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Optional<Manufacturer> result;
        String getForIdString = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false";
        try (Connection connection = connectToDB.getConnection();
                PreparedStatement getForIdStatement =
                        connection.prepareStatement(getForIdString)) {
            getForIdStatement.setString(1, id.toString());
            ResultSet resultSet = getForIdStatement.executeQuery();
            result = resultSet.next()
                    ? Optional.of(getInstanceManufacturer(resultSet)) : Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Can`t get record by id from DB", e);
        }
        return result;
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers;
        String getAllString = "SELECT * FROM manufacturers WHERE is_deleted = false";
        try (Connection connection = connectToDB.getConnection();
                PreparedStatement getAllStatement =
                        connection.prepareStatement(getAllString)) {
            ResultSet resultSet = getAllStatement.executeQuery();
            manufacturers = new ArrayList<>();
            while (resultSet.next()) {
                manufacturers.add(getInstanceManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can`t get records from DB", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRecordString = "UPDATE manufacturers SET name = ?, country = ? WHERE id = ?";
        try (Connection connection = connectToDB.getConnection();
                PreparedStatement updateRecordStatement =
                        connection.prepareStatement(updateRecordString)) {
            updateRecordStatement.setString(1, manufacturer.getName());
            updateRecordStatement.setString(2, manufacturer.getCountry());
            updateRecordStatement.setString(3, manufacturer.getId().toString());
            updateRecordStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return get(manufacturer.getId()).orElse(null);
    }

    @Override
    public boolean delete(Long id) {
        int res;
        String deleteRecordString = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
        try (Connection connection = connectToDB.getConnection();
                PreparedStatement deleteRecordStatement =
                        connection.prepareStatement(deleteRecordString)) {
            deleteRecordStatement.setString(1, id.toString());
            res = deleteRecordStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can`t delete record", e);
        }
        return res > 0;
    }

    private Manufacturer getInstanceManufacturer(ResultSet resultSet) {
        try {
            return new Manufacturer.Builder()
                    .setId(resultSet.getObject("id", Long.class))
                    .setName(resultSet.getString("name"))
                    .setCountry(resultSet.getString("country")).build();
        } catch (SQLException e) {
            throw new RuntimeException("Can`t get instance of class Manufacturer", e);
        }
    }
}
