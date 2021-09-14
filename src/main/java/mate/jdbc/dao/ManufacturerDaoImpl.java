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
    private static final String INSERT_MANUFACTURER_REQUEST
            = "INSERT INTO manufacturers(name, country) values(?,?);";
    private static final String GET_ALL_MANUFACTURERS_REQUEST
            = "SELECT * FROM manufacturers WHERE is_deleted = false";
    private static final String UPDATE_MANUFACTURER_REQUEST
            = "UPDATE manufacturers SET name=?,country=? WHERE id=?";
    private static final String DELETE_MANUFACTURER_REQUEST
            = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
    private static final String GET_MANUFACTURER_REQUEST
            = "SELECT * FROM manufacturers WHERE is_deleted = false AND id = ?";
    private static final String CANT_GET_ALL_MESSAGE = "Can't get all manufacturers from DB.";
    private static final String CANT_CREATE_MESSAGE = "Can't insert manufacturer to DB.";
    private static final String CANT_UPDATE_MESSAGE = "Can't update manufacturer in DB.";
    private static final String CANT_DELETE_MESSAGE = "Can't delete manufacturer from DB.";
    private static final String CANT_GET_MESSAGE = "Can't get manufacturer by id from DB.";
    private static final String NAME = "name";
    private static final String COUNTRY = "country";
    private static final String ID = "id";

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturerList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllManufacturersStatement = connection.createStatement();) {
            ResultSet resultSet = getAllManufacturersStatement
                    .executeQuery(GET_ALL_MANUFACTURERS_REQUEST);
            while (resultSet.next()) {
                String name = resultSet.getString(NAME);
                String country = resultSet.getString(COUNTRY);
                Long id = resultSet.getObject(ID, Long.class);
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(id);
                manufacturer.setName(name);
                manufacturer.setCountry(country);
                manufacturerList.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(CANT_GET_ALL_MESSAGE, e);
        }
        return manufacturerList;
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement =
                        connection.prepareStatement(INSERT_MANUFACTURER_REQUEST,
                            Statement.RETURN_GENERATED_KEYS);) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(CANT_CREATE_MESSAGE, e);
        }
        return manufacturer;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement =
                        connection.prepareStatement(UPDATE_MANUFACTURER_REQUEST,
                            Statement.RETURN_GENERATED_KEYS);) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setObject(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(CANT_UPDATE_MESSAGE, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement = connection
                        .prepareStatement(DELETE_MANUFACTURER_REQUEST,
                            Statement.RETURN_GENERATED_KEYS);) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new RuntimeException(CANT_DELETE_MESSAGE, e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Manufacturer manufacturer = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement =
                        connection.prepareStatement(GET_MANUFACTURER_REQUEST,
                            Statement.RETURN_GENERATED_KEYS);) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            while (resultSet.next()) {
                manufacturer = new Manufacturer();
                String name = resultSet.getString(NAME);
                String country = resultSet.getString(COUNTRY);
                manufacturer.setId(id);
                manufacturer.setName(name);
                manufacturer.setCountry(country);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new RuntimeException(CANT_GET_MESSAGE, e);
        }
    }
}
