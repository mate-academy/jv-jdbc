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
import mate.jdbc.util.ConnectionMySqlUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private final ConnectionMySqlUtil connectionMySqlUtil = new ConnectionMySqlUtil();

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String createManufacturerRequest = "INSERT INTO manufacturer(name,country) values(?,?);";
        try (Connection connection = connectionMySqlUtil.getConnection();
                PreparedStatement createManufacturerStatement = connection
                        .prepareStatement(createManufacturerRequest,
                                Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1,manufacturer.getName());
            createManufacturerStatement.setString(2,manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long idManufacturerFromDb = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(idManufacturerFromDb);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer to DB "
                    + manufacturer,e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturerRequest
                = "SELECT * FROM manufacturer WHERE is_deleted = false AND id = ?;";
        Manufacturer manufacturerFromDB = new Manufacturer();
        try (Connection connection = connectionMySqlUtil.getConnection();
                PreparedStatement getManufacturerStatement
                        = connection.prepareStatement(getManufacturerRequest)) {
            getManufacturerStatement.setString(1,id.toString());
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            if (resultSet.next()) {
                manufacturerFromDB = getManufacturerFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer form DB",e);
        }
        return Optional.of(manufacturerFromDB);
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturerFromDB = new ArrayList<>();
        String getAllManufacturersRequest
                = "SELECT * FROM manufacturer WHERE is_deleted = false;";
        try (Connection connection = connectionMySqlUtil.getConnection();
                PreparedStatement getAllManufacturersStatement
                        = connection.prepareStatement(getAllManufacturersRequest)) {
            ResultSet resultSet = getAllManufacturersStatement.executeQuery();
            while (resultSet.next()) {
                Manufacturer manufacturerFromDB = getManufacturerFromResultSet(resultSet);
                allManufacturerFromDB.add(manufacturerFromDB);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers form DB",e);
        }
        return allManufacturerFromDB;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerRequest = "UPDATE manufacturer SET name = ? , country = ?  "
                + "WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = connectionMySqlUtil.getConnection();
                PreparedStatement updateManufacturerStatement
                        = connection.prepareStatement(updateManufacturerRequest)) {
            updateManufacturerStatement.setString(1,manufacturer.getName());
            updateManufacturerStatement.setString(2,manufacturer.getCountry());
            updateManufacturerStatement.setLong(3,manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer in DB", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturer SET is_deleted = TRUE WHERE id = ?;";
        try (Connection connection = connectionMySqlUtil.getConnection();
                PreparedStatement deleteRequestStatement =
                        connection.prepareStatement(deleteRequest,
                             Statement.RETURN_GENERATED_KEYS)) {
            deleteRequestStatement.setLong(1,id);
            return deleteRequestStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer into DB",e);
        }
    }

    private Manufacturer getManufacturerFromResultSet(ResultSet resultSet) {
        Manufacturer manufacturer = new Manufacturer();
        try {
            Long manufacturerId = resultSet.getObject(1, Long.class);
            String manufacturerName = resultSet.getString("name");
            String manufacturerCountry = resultSet.getString("country");
            manufacturer.setId(manufacturerId);
            manufacturer.setName(manufacturerName);
            manufacturer.setCountry(manufacturerCountry);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer from ResultSet "
                    + manufacturer,e);
        }
        return manufacturer;
    }
}
