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
    private static final String ID_COLUMN_NAME = "id";
    private static final String NAME_COLUMN_NAME = "name";
    private static final String COUNTRY_COLUMN_NAME = "country";
    private static final String IS_DELETED_COLUMN_NAME = "is_deleted";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String createManufacturerRequest
                = "INSERT INTO manufacturers (name, country) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createStatement = connection
                        .prepareStatement(createManufacturerRequest,
                                Statement.RETURN_GENERATED_KEYS)) {
            createStatement.setString(1, manufacturer.getName());
            createStatement.setString(2, manufacturer.getCountry());
            createStatement.executeUpdate();
            ResultSet generatedKeys = createStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long generatedId = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(generatedId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't insert new manufacturer in DB with parameters. "
                    + "Name: " + manufacturer.getName() + " ,country: " + manufacturer.getCountry(), e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturerRequest = "SELECT * FROM manufacturers WHERE id = ?;";
        Optional<Manufacturer> optionalManufacturer = Optional.empty();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement =
                        connection.prepareStatement(getManufacturerRequest)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            if (resultSet.next()) {
                if (!resultSet.getBoolean(IS_DELETED_COLUMN_NAME)) {
                    Manufacturer manufacturer = getManufacturerByResultSet(resultSet);
                    optionalManufacturer = Optional.of(manufacturer);
                }
            }
            return optionalManufacturer;
        } catch (SQLException e) {
            throw new RuntimeException("Can't get manufacturer from DB by ID: " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllManufacturersRequest = "SELECT * FROM manufacturers";
        List<Manufacturer> manufacturerList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
            Statement getAllManufacturersStatement = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturersStatement
                    .executeQuery(getAllManufacturersRequest);
            while (resultSet.next()) {
                if (!resultSet.getBoolean(IS_DELETED_COLUMN_NAME)) {
                    Manufacturer manufacturer = getManufacturerByResultSet(resultSet);
                    manufacturerList.add(manufacturer);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get all manufacturers from DB", e);
        }
        return manufacturerList;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerRequest =
                "UPDATE manufacturers SET name = ?, country = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement =
                    connection.prepareStatement(updateManufacturerRequest)) {
            if (get(manufacturer.getId()).isEmpty()) {
                return null;
            }
            Manufacturer oldManufacturer = get(manufacturer.getId()).get();
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
            return oldManufacturer;
        } catch (SQLException e) {
            throw new RuntimeException("Can't update manufacturer in DB with ID: "
                    + manufacturer.getId(), e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufacturerRequest = "UPDATE manufacturers SET is_deleted = 1 WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
            PreparedStatement deleteManufacturerStatement =
                connection.prepareStatement(deleteManufacturerRequest)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete manufacturer from DB with ID: " + id, e);
        }
    }

    private Manufacturer getManufacturerByResultSet(ResultSet resultSet) {
        Manufacturer manufacturer = new Manufacturer();
        try {
            manufacturer.setName(resultSet.getString(NAME_COLUMN_NAME));
            manufacturer.setId(resultSet.getObject(ID_COLUMN_NAME, Long.class));
            manufacturer.setCountry(resultSet.getString(COUNTRY_COLUMN_NAME));
        } catch (SQLException e) {
            throw new RuntimeException("Can't create manufacturer from result set", e);
        }
        return manufacturer;
    }
}
