package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final int NUM_OF_UPDATE_ROW = 0;
    private static final int ID_INDEX = 1;
    private static final int NAME_INDEX = 1;
    private static final int COUNTRY_INDEX = 2;
    private static final int IS_DELETED_INDEX = 3;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest =
                "INSERT INTO manufacturers (name , country, is_deleted) VALUES (?,?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement = connection
                           .prepareStatement(insertManufacturerRequest,
                                   Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(NAME_INDEX, manufacturer.getName());
            createManufacturerStatement.setString(COUNTRY_INDEX, manufacturer.getCountry());
            createManufacturerStatement.setBoolean(IS_DELETED_INDEX, false);
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(ID_INDEX, Long.class);
                manufacturer.setId(id);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can`t insert manufacturer " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String deleteRequest = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement =
                        connection.prepareStatement(deleteRequest)) {
            getManufacturerStatement.setLong(ID_INDEX, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(createManufacturerObj(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get manufacturer by id " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllRequest = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        List<Manufacturer> manufacturerList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturerStatement =
                        connection.prepareStatement(getAllRequest)) {
            ResultSet resultSet = getAllManufacturerStatement.executeQuery();
            while (resultSet.next()) {
                manufacturerList.add(createManufacturerObj(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all manufacturers from DB", e);
        }
        return manufacturerList;
    }

    private Manufacturer createManufacturerObj(ResultSet resultSet) {
        Manufacturer manufacturer = new Manufacturer();
        try {
            manufacturer.setId(resultSet.getObject("id", Long.class));
            manufacturer.setName(resultSet.getString("name"));
            manufacturer.setCountry(resultSet.getString("country"));
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get manufacturer from DB", e);
        }
        return manufacturer;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        if (manufacturer.getId() == null) {
            throw new DataProcessingException("Can`t update manufacturer with null id.",
                    new Throwable(""));
        }
        String updatedRequest = "UPDATE manufacturers SET name  = ?, country = ? where id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updatedStatement = connection.prepareStatement(updatedRequest)) {
            updatedStatement.setString(NAME_INDEX, manufacturer.getName());
            updatedStatement.setString(COUNTRY_INDEX, manufacturer.getCountry());
            updatedStatement.setString(3, String.valueOf(manufacturer.getId()));
            updatedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update manufacturer " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true where id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement softDeleted = connection.prepareStatement(deleteRequest)) {
            softDeleted.setLong(ID_INDEX, id);
            return softDeleted.executeUpdate() > NUM_OF_UPDATE_ROW;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete manufacturer by id " + id, e);
        }
    }
}
