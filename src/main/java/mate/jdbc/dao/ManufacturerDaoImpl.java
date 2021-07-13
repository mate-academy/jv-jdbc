package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_COUNTRY = "country";
    private static final int VALUE_INDEX_ONE = 1;
    private static final int VALUE_INDEX_TWO = 2;
    private static final int VALUE_INDEX_THREE = 3;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String createStatement = "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement createManufacturersStatement =
                         connection.prepareStatement(createStatement,
                                 Statement.RETURN_GENERATED_KEYS)) {
            createManufacturersStatement.setString(VALUE_INDEX_ONE, manufacturer.getName());
            createManufacturersStatement.setString(VALUE_INDEX_TWO, manufacturer.getCountry());
            createManufacturersStatement.executeUpdate();
            ResultSet resultSet = createManufacturersStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Long currentId = resultSet.getObject(VALUE_INDEX_ONE, Long.class);
                manufacturer.setId(currentId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can not creat manufacturer = "
                    + manufacturer + " in DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getStatement = "SELECT * FROM manufacturers WHERE is_deleted = false AND id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getManufacturersStatement =
                         connection.prepareStatement(getStatement)) {
            getManufacturersStatement.setLong(VALUE_INDEX_ONE, id);
            ResultSet resultSet = getManufacturersStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getManufacture(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can not get manufacturer with id = "
                    + id + " from DB", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String getAllStatement = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturersStatement
                         = connection.prepareStatement(getAllStatement)) {
            ResultSet resultSet = getAllManufacturersStatement.executeQuery();
            while (resultSet.next()) {
                allManufacturers.add(getManufacture(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can not get all manufacturers from DB", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateStatement =
                "UPDATE manufacturers SET name = ?, country = ?"
                        + " WHERE is_deleted = false AND id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturersStatement =
                         connection.prepareStatement(updateStatement)) {
            updateManufacturersStatement.setString(VALUE_INDEX_ONE, manufacturer.getName());
            updateManufacturersStatement.setString(VALUE_INDEX_TWO, manufacturer.getCountry());
            updateManufacturersStatement.setObject(VALUE_INDEX_THREE, manufacturer.getId());
            updateManufacturersStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new RuntimeException("Can not update manufacturer = "
                    + manufacturer + " in DB", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteStatement = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement deleteManufacturersStatement =
                        connection.prepareStatement(deleteStatement)) {
            deleteManufacturersStatement.setLong(VALUE_INDEX_ONE, id);
            return deleteManufacturersStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Can not delete manufacturer with id = "
                    + id + " in DB", e);
        }
    }

    private Manufacturer getManufacture(ResultSet resultSet) {
        try {
            Long id = resultSet.getObject(COLUMN_ID, Long.class);
            String name = resultSet.getString(COLUMN_NAME);
            String country = resultSet.getString(COLUMN_COUNTRY);
            Manufacturer manufacturer = new Manufacturer(name, country);
            manufacturer.setId(id);
            return manufacturer;
        } catch (SQLException e) {
            throw new RuntimeException("Can not get manufacturer from DB", e);
        }
    }
}
