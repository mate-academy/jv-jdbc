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
    private static final String CRATE_MANUFACTURER_STATEMENT =
            "INSERT INTO manufacturers(manufacturer_name, manufacturer_country) values(?, ?)";
    private static final String SOFT_DELETE_MANUFACTURER_STATEMENT =
            "UPDATE manufacturers SET is_deleted = true WHERE manufacturer_id = ?";
    private static final String GET_MANUFACTURER_ID_STATEMENT =
            "SELECT * FROM manufacturers WHERE is_deleted = false AND manufacturer_id = ?";
    private static final String GET_ALL_MANUFACTURERS_STATEMENT =
            "SELECT * FROM manufacturers WHERE is_deleted = false";
    private static final String UPDATE_MANUFACTURER_STATEMENT =
            "UPDATE manufacturers SET manufacturer_name = ?, manufacturer_country = ?"
                    + "WHERE is_deleted = false AND manufacturer_id = ?";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement createManufacturerStatement =
                         connection.prepareStatement(CRATE_MANUFACTURER_STATEMENT,
                             Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t create new manufacturer", e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement deleteManufacturer =
                         connection.prepareStatement(SOFT_DELETE_MANUFACTURER_STATEMENT)) {
            deleteManufacturer.setLong(1, id);
            return deleteManufacturer.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete manufacturer with id = " + id, e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getManufacturer =
                         connection.prepareStatement(GET_MANUFACTURER_ID_STATEMENT)) {
            getManufacturer.setLong(1, id);
            ResultSet resultSet = getManufacturer.executeQuery();
            if (!resultSet.next()) {
                throw new RuntimeException("Record with id = " + id
                        + " does not exist or deleted!");
            } else {
                Long manufacturerId = resultSet.getObject("manufacturer_id", Long.class);
                String manufacturerName = resultSet.getString("manufacturer_name");
                String manufacturerCountry =
                        resultSet.getString("manufacturer_country");
                return Optional.of(
                        new Manufacturer(manufacturerId, manufacturerName, manufacturerCountry));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get manufacturer with id = " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturersList = new ArrayList<>();
        try (Connection connection =
                     ConnectionUtil.getConnection();
                 Statement getAllManufacturersStatement = connection.createStatement()) {
            ResultSet resultSet =
                    getAllManufacturersStatement.executeQuery(GET_ALL_MANUFACTURERS_STATEMENT);
            while (resultSet.next()) {
                Long manufacturerId = resultSet.getObject("manufacturer_id", Long.class);
                String manufacturerName = resultSet.getString("manufacturer_name");
                String manufacturerCountry =
                        resultSet.getString("manufacturer_country");
                Manufacturer manufacturer =
                        new Manufacturer(manufacturerId, manufacturerName, manufacturerCountry);
                allManufacturersList.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all formats from DB", e);
        }
        return allManufacturersList;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement updateManufacturerStatement =
                         connection.prepareStatement(UPDATE_MANUFACTURER_STATEMENT)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            if (updateManufacturerStatement.executeUpdate() > 0) {
                return manufacturer;
            } else {
                throw new RuntimeException("Can`t update record with id = " + manufacturer.getId());
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t create new manufacturer", e);
        }
    }
}
