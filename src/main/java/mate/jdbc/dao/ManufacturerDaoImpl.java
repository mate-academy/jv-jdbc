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
    private static final String GET_BY_ID_QUERY = "SELECT * FROM manufacturers "
            + "WHERE manufacturer_id = ? AND is_deleted = false";
    private static final String GETALL_QUERY = "SELECT * FROM manufacturers "
            + "WHERE is_deleted = false";
    private static final String SOFT_DELETE_QUERY = "UPDATE manufacturers "
            + "SET is_deleted = true WHERE manufacturer_id = ?";
    private static final String UPDATE_QUERY = "UPDATE manufacturers SET manufacturer_name = ?,"
            + "manufacturer_country = ? WHERE manufacturer_id = ? AND is_deleted = false";
    private static final String INSERT_QUERY = "INSERT INTO manufacturers("
            + "manufacturer_name, manufacturer_country) VALUES(?, ?);";
    private static final String MANUFACTURER_ID_COLUMN = "manufacturer_id";
    private static final String MANUFACTURER_NAME_COLUMN = "manufacturer_name";
    private static final String MANUFACTURER_COUNTRY_COLUMN = "manufacturer_country";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        if (manufacturer == null) {
            throw new RuntimeException("Manufacturer is null!");
        }

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement insertManufactureStatement =
                        connection.prepareStatement(INSERT_QUERY,
                                Statement.RETURN_GENERATED_KEYS)) {
            insertManufactureStatement.setString(1, manufacturer.getName());
            insertManufactureStatement.setString(2, manufacturer.getCountry());
            insertManufactureStatement.executeUpdate();
            ResultSet generatedKeys = insertManufactureStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                manufacturer.setId(generatedKeys.getObject(1, Long.class));
            }
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't insert manufacture to DB!", throwables);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Manufacturer manufacturer = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturersStatement =
                        connection.prepareStatement(GET_BY_ID_QUERY)) {
            getManufacturersStatement.setLong(1, id);
            ResultSet resultSet = getManufacturersStatement.executeQuery();
            if (resultSet.next()) {
                manufacturer = createManufacturer(resultSet);
            }
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't get manufacturer from DB with such id: "
                    + id, throwables);
        }
        return Optional.ofNullable(manufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturerList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllManufacturersStatement =
                        connection.createStatement()) {
            ResultSet resultSet = getAllManufacturersStatement.executeQuery(
                    GETALL_QUERY);
            while (resultSet.next()) {
                manufacturerList.add(createManufacturer(resultSet));
            }
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't get all manufacturers from DB!", throwables);
        }
        return manufacturerList;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufactureStatement =
                        connection.prepareStatement(UPDATE_QUERY,
                                Statement.RETURN_GENERATED_KEYS)) {
            updateManufactureStatement.setString(1, manufacturer.getName());
            updateManufactureStatement.setString(2, manufacturer.getCountry());
            updateManufactureStatement.setLong(3, manufacturer.getId());
            if (updateManufactureStatement.executeUpdate() >= 1) {
                return manufacturer;
            }
            throw new RuntimeException("There are no such manufacturer in DB: "
                    + manufacturer);
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't update manufacture "
                    + manufacturer + " from DB!", throwables);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement insertManufactureStatement =
                        connection.prepareStatement(SOFT_DELETE_QUERY,
                             Statement.RETURN_GENERATED_KEYS)) {
            insertManufactureStatement.setLong(1, id);
            return insertManufactureStatement.executeUpdate() >= 1;
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't delete manufacturer from DB!", throwables);
        }
    }

    private Manufacturer createManufacturer(ResultSet resultSetLine) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSetLine.getObject(MANUFACTURER_ID_COLUMN, Long.class));
        manufacturer.setName(resultSetLine.getString(MANUFACTURER_NAME_COLUMN));
        manufacturer.setCountry(resultSetLine.getString(MANUFACTURER_COUNTRY_COLUMN));
        return manufacturer;
    }
}
