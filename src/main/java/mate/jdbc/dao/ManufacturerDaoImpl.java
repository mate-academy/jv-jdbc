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
import mate.jdbc.util.ConnectionMySqlUtilImpl;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private final ConnectionUtil connectionUtil = new ConnectionMySqlUtilImpl();

    @Override
    public List<Manufacturer> createAll(List<Manufacturer> manufacturers) {
        String createAllManufacturersRequest
                = "INSERT INTO manufacturer(name,country) values(?,?);";
        try (Connection connection = connectionUtil.getConnection();
                PreparedStatement createFormatStatement = connection
                        .prepareStatement(createAllManufacturersRequest,
                             Statement.RETURN_GENERATED_KEYS)) {
            for (Manufacturer manufacturer : manufacturers) {
                createFormatStatement.setString(1,manufacturer.getName());
                createFormatStatement.setString(2,manufacturer.getCountry());
                createFormatStatement.executeUpdate();
                ResultSet generatedKeys = createFormatStatement.getGeneratedKeys();
                Long idManufacturerFromDb = getIdManufacturerFromDb(generatedKeys);
                manufacturer.setId(idManufacturerFromDb);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturers to DB "
                    + manufacturers,e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String createManufacturerRequest = "INSERT INTO manufacturer(name,country) values(?,?);";
        try (Connection connection = connectionUtil.getConnection();
                PreparedStatement createManufacturerStatement = connection
                        .prepareStatement(createManufacturerRequest,
                              Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1,manufacturer.getName());
            createManufacturerStatement.setString(2,manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            Long idManufacturerFromDb = getIdManufacturerFromDb(generatedKeys);
            manufacturer.setId(idManufacturerFromDb);
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
        try (Connection connection = connectionUtil.getConnection();
                PreparedStatement getManufacturerStatement
                        = connection.prepareStatement(getManufacturerRequest)) {
            getManufacturerStatement.setString(1,id.toString());
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            Long manufacturerId = getIdManufacturerFromDb(resultSet);
            if (resultSet.next()) {
                String manufacturerName = resultSet.getString("name");
                String manufacturerCountry = resultSet.getString("country");
                manufacturerFromDB.setId(manufacturerId);
                manufacturerFromDB.setName(manufacturerName);
                manufacturerFromDB.setCountry(manufacturerCountry);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get manufacturer form DB",e);
        }
        return Optional.of(manufacturerFromDB);
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllManufacturersRequest
                = "SELECT * FROM manufacturer WHERE is_deleted = false;";
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = connectionUtil.getConnection();
                PreparedStatement getAllManufacturersStatement
                        = connection.prepareStatement(getAllManufacturersRequest)) {
            ResultSet resultSet = getAllManufacturersStatement.executeQuery();
            while (resultSet.next()) {
                Long manufacturerId = resultSet.getObject("id", Long.class);
                String manufacturerName = resultSet.getString("name");
                String manufacturerCountry = resultSet.getString("country");
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(manufacturerId);
                manufacturer.setName(manufacturerName);
                manufacturer.setCountry(manufacturerCountry);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get all manufacturers form DB",e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerRequest = "UPDATE manufacturer SET name = ? , country = ?  "
                + "WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = connectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement
                        = connection.prepareStatement(updateManufacturerRequest)) {
            updateManufacturerStatement.setString(1,manufacturer.getName());
            updateManufacturerStatement.setString(2,manufacturer.getCountry());
            updateManufacturerStatement.setLong(3,manufacturer.getId());
            if (updateManufacturerStatement.executeUpdate() > 0) {
                return manufacturer;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get manufacturer form DB", e);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturer SET is_deleted = TRUE WHERE id = ?;";
        try (Connection connection = connectionUtil.getConnection();
                PreparedStatement deleteRequestStatement =
                        connection.prepareStatement(deleteRequest,
                                Statement.RETURN_GENERATED_KEYS)) {
            deleteRequestStatement.setLong(1,id);
            return deleteRequestStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete manufacturer into DB",e);
        }
    }

    private Long getIdManufacturerFromDb(ResultSet generatedKeys) {
        try {
            generatedKeys.next();
            return generatedKeys.getObject(1, Long.class);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get id manufacturer from DB ",e);
        }
    }
}
