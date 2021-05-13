package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.exceptions.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final String GET_BY_ID_QUERY = "SELECT * FROM manufacturers "
            + "WHERE manufacturer_id = ? AND is_deleted = false";
    private static final String GET_ALL_QUERY = "SELECT * FROM manufacturers "
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
        } catch (SQLException throwable) {
            throw new DataProcessingException("Can't insert manufacture "
                    + manufacturer + " to DB!", throwable);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Manufacturer manufacturer = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement =
                        connection.prepareStatement(GET_BY_ID_QUERY)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            if (resultSet.next()) {
                manufacturer = createManufacturer(resultSet);
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't get manufacturer from DB with such id: "
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
                    GET_ALL_QUERY);
            while (resultSet.next()) {
                manufacturerList.add(createManufacturer(resultSet));
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't get all manufacturers from DB!", throwables);
        }
        return manufacturerList;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufactureStatement =
                        connection.prepareStatement(UPDATE_QUERY)) {
            updateManufactureStatement.setString(1, manufacturer.getName());
            updateManufactureStatement.setString(2, manufacturer.getCountry());
            updateManufactureStatement.setLong(3, manufacturer.getId());
            if (updateManufactureStatement.executeUpdate() > 0) {
                return manufacturer;
            }
            throw new DataProcessingException("There are no such manufacturer in DB: "
                    + manufacturer);
        } catch (SQLException throwable) {
            throw new DataProcessingException("Can't update manufacture "
                    + manufacturer + " in DB!", throwable);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement =
                        connection.prepareStatement(SOFT_DELETE_QUERY)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() > 0;
        } catch (SQLException throwable) {
            throw new RuntimeException("Can't delete manufacturer with id: "
                    + id + " from DB!", throwable);
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
