package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.customexceptions.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final int PARAMETER_INDEX_ONE = 1;
    private static final int PARAMETER_INDEX_TWO = 2;
    private static final int PARAMETER_INDEX_THREE = 3;
    private static final int PARAMETER_INDEX_FOUR = 4;
    private static final String ID_COLUMN_LABEL = "id";
    private static final String NAME_COLUMN_LABEL = "name";
    private static final String COUNTRY_COLUMN_LABEL = "country";
    private static final String DELETE_MANUFACTURER_REQUEST =
            "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
    private static final String UPDATE_MANUFACTURER_REQUEST =
            "UPDATE manufacturers SET id = ?, name = ?,"
                    + "country = ? WHERE id = ? AND is_deleted = false";
    private static final String GET_ALL_MANUFACTURERS_REQUEST =
            "SELECT * FROM manufacturers where is_deleted = false";
    private static final String GET_BY_ID_MANUFACTURER_REQUEST
            = "SELECT * FROM manufacturers where id = ? AND is_deleted = false";
    private static final String INSERT_MANUFACTURER_REQUEST
            = "INSERT INTO manufacturers(name,country) values(?,?)";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                    PreparedStatement createManufacturerStatement = connection
                        .prepareStatement(
                                INSERT_MANUFACTURER_REQUEST, Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(PARAMETER_INDEX_ONE, manufacturer.getName());
            createManufacturerStatement.setString(PARAMETER_INDEX_TWO, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            while (generatedKeys.next()) {
                Long manufacturerId = generatedKeys.getObject(PARAMETER_INDEX_ONE, Long.class);
                manufacturer.setId(manufacturerId);
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can`t insert manufacturer to DB"
                    + manufacturer, throwables);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement =
                        connection.prepareStatement(GET_BY_ID_MANUFACTURER_REQUEST)) {
            getManufacturerStatement.setLong(PARAMETER_INDEX_ONE, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            Manufacturer manufacturer = null;
            if (resultSet.next()) {
                Long manufacturerId = resultSet.getObject(ID_COLUMN_LABEL, Long.class);
                String manufacturerName = resultSet.getString(NAME_COLUMN_LABEL);
                String manufacturerCountry = resultSet.getString(COUNTRY_COLUMN_LABEL);
                manufacturer =
                        new Manufacturer(manufacturerId, manufacturerName, manufacturerCountry);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can`t get manufacturer by id" + id, throwables);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                    PreparedStatement getAllManufacturesStatement
                            = connection.prepareStatement(GET_ALL_MANUFACTURERS_REQUEST)) {
            ResultSet resultSet = getAllManufacturesStatement
                    .executeQuery();
            while (resultSet.next()) {
                Long manufacturerId = resultSet.getObject(ID_COLUMN_LABEL, Long.class);
                String manufacturerName = resultSet.getString(NAME_COLUMN_LABEL);
                String manufacturerCountry = resultSet.getString(COUNTRY_COLUMN_LABEL);
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(manufacturerId);
                manufacturer.setName(manufacturerName);
                manufacturer.setCountry(manufacturerCountry);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can`t get all manufacturers from DB", throwables);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        Long manufacturerId = manufacturer.getId();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement =
                        connection.prepareStatement(UPDATE_MANUFACTURER_REQUEST)) {
            updateManufacturerStatement.setLong(PARAMETER_INDEX_ONE,manufacturerId);
            updateManufacturerStatement.setString(PARAMETER_INDEX_TWO, manufacturer.getName());
            updateManufacturerStatement.setString(PARAMETER_INDEX_THREE, manufacturer.getCountry());
            updateManufacturerStatement.setLong(PARAMETER_INDEX_FOUR, manufacturerId);
            updateManufacturerStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can`t update manufacturer"
                    + manufacturer, throwables);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement =
                        connection.prepareStatement(DELETE_MANUFACTURER_REQUEST)) {
            deleteManufacturerStatement.setLong(PARAMETER_INDEX_ONE, id);
            return deleteManufacturerStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can`t delete manufacturer by id" + id, throwables);
        }
    }
}
