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
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    public static final String GET_ALL_REQUEST
            = "SELECT * FROM manufacturers WHERE is_deleted = FALSE";
    public static final String INSERT_MANUFACTURER_REQUEST
            = "INSERT INTO manufacturers(manufacturer, country) VALUES(?, ?)";
    public static final String DELETE_MANUFACTURER_BY_ID_REQUEST
            = "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ?";
    public static final String UPDATE_MANUFACTURER_BY_ID_REQUEST
            = "UPDATE manufacturers SET manufacturer = ?, country = ? "
            + "WHERE id = ? AND is_deleted = FALSE";
    public static final String GET_MANUFACTURER_BY_ID_REQUEST
            = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = FALSE";
    public static final String MANUFACTURER_COLUMN_NAME = "manufacturer";
    public static final String COUNTRY_COLUMN_NAME = "country";
    public static final String ID_COLUMN_NAME = "id";
    public static final int ID_COLUMN_INDEX = 1;
    public static final int MANUFACTURER_INDEX = 1;
    public static final int COUNTRY_INDEX = 2;
    public static final int ID_INDEX_FOR_UPDATE = 3;
    public static final int ID_INDEX_FOR_DELETE = 1;
    public static final int ID_INDEX_FOR_GET = 1;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement = connection
                        .prepareStatement(INSERT_MANUFACTURER_REQUEST,
                                Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(MANUFACTURER_INDEX, manufacturer.getName());
            createManufacturerStatement.setString(COUNTRY_INDEX, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                manufacturer.setId(generatedKeys.getObject(ID_COLUMN_INDEX, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t insert manufacturer "
                    + manufacturer.getName()
                    + " to DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Manufacturer manufacturer = new Manufacturer();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement = connection
                        .prepareStatement(GET_MANUFACTURER_BY_ID_REQUEST)) {
            getManufacturerStatement.setLong(ID_INDEX_FOR_GET, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            if (resultSet.next()) {
                manufacturer = getManufacture(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get manufacturer by id: " + id, e);
        }
        return Optional.of(manufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturersStatement = connection
                        .prepareStatement(GET_ALL_REQUEST)) {
            ResultSet resultSet = getAllManufacturersStatement.executeQuery();
            while (resultSet.next()) {
                allManufacturers.add(getManufacture(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all manufacturers from DB", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement = connection
                        .prepareStatement(UPDATE_MANUFACTURER_BY_ID_REQUEST)) {
            updateStatement.setString(MANUFACTURER_INDEX, manufacturer.getName());
            updateStatement.setString(COUNTRY_INDEX, manufacturer.getCountry());
            updateStatement.setLong(ID_INDEX_FOR_UPDATE, manufacturer.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update manufacturer: "
                    + manufacturer.getName(), e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteStatement = connection
                        .prepareStatement(DELETE_MANUFACTURER_BY_ID_REQUEST)) {
            deleteStatement.setLong(ID_INDEX_FOR_DELETE, id);
            return deleteStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete manufacturer by ID: "
                    + id + " from DB", e);
        }
    }

    private Manufacturer getManufacture(ResultSet resultSet) {
        Manufacturer manufacturer = new Manufacturer();
        try {
            manufacturer.setId(resultSet.getObject(ID_COLUMN_NAME, Long.class));
            manufacturer.setName(resultSet.getString(MANUFACTURER_COLUMN_NAME));
            manufacturer.setCountry(resultSet.getString(COUNTRY_COLUMN_NAME));
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get manufacturer from resultSet", e);
        }
        return manufacturer;
    }
}
