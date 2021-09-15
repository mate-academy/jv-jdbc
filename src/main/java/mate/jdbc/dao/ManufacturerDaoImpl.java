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
        String getAllManufacturersRequest
                = "SELECT * FROM manufacturers WHERE is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturersStatement =
                            connection.prepareStatement(getAllManufacturersRequest)) {
            ResultSet resultSet = getAllManufacturersStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString(NAME);
                String country = resultSet.getString(COUNTRY);
                Long id = resultSet.getObject(ID, Long.class);
                Manufacturer manufacturer = new Manufacturer(name, country);
                manufacturer.setId(id);
                manufacturerList.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(CANT_GET_ALL_MESSAGE, e);
        }
        return manufacturerList;
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest
                = "INSERT INTO manufacturers(name, country) values(?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement =
                            connection.prepareStatement(insertManufacturerRequest,
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
        String updateManufacturerRequest
                = "UPDATE manufacturers SET name=?,country=? WHERE id=?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement =
                            connection.prepareStatement(updateManufacturerRequest,
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
        String deleteManufacturerRequest
                = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement = connection
                            .prepareStatement(deleteManufacturerRequest,
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
        String getManufacturerRequest
                = "SELECT * FROM manufacturers WHERE is_deleted = false AND id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement =
                        connection.prepareStatement(getManufacturerRequest)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString(NAME);
                String country = resultSet.getString(COUNTRY);
                manufacturer = new Manufacturer(name, country);
                manufacturer.setId(id);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new RuntimeException(CANT_GET_MESSAGE, e);
        }
    }
}
