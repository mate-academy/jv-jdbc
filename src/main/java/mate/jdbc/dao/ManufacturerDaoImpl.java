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
    private static final int MANUFACTURER_ID = 1;
    private static final int MANUFACTURER_NAME = 2;
    private static final int MANUFACTURER_COUNTRY = 3;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest = "INSERT INTO manufacturers(name, country) VALUES(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerRaw
                        = connection.prepareStatement(insertManufacturerRequest,
                        Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerRaw.setString(1, manufacturer.getName());
            createManufacturerRaw.setString(2, manufacturer.getCountry());
            createManufacturerRaw.executeUpdate();
            ResultSet generatedKeys = createManufacturerRaw.getGeneratedKeys();
            if (generatedKeys.next()) {
                manufacturer.setId(generatedKeys.getObject(MANUFACTURER_ID, Long.class));
            }
            return manufacturer;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can`t creat new raw in DB for "
                    + manufacturer, ex);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturerRequest = "SELECT * FROM manufacturers "
                + "WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement
                        = connection.prepareStatement(getManufacturerRequest)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            Manufacturer manufacturer = null;
            if (resultSet.next()) {
                manufacturer = getDataFromResultSet(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException ex) {
            throw new DataProcessingException("Can`t get manufacturer from DB by id = "
                    + id, ex);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturersList = new ArrayList<>();
        String getAllManufacturerRequest = "SELECT * FROM manufacturers "
                + "WHERE is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturersStatement
                            = connection.prepareStatement(getAllManufacturerRequest)) {
            ResultSet resultSet = getAllManufacturersStatement.executeQuery();
            while (resultSet.next()) {
                manufacturersList.add(getDataFromResultSet(resultSet));
            }
            return manufacturersList;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can`t get all manufacturers from DB", ex);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerRequest
                = "UPDATE manufacturers SET name = ?, country = ? "
                + "WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement
                            = connection.prepareStatement(updateManufacturerRequest)) {
            updateManufacturerStatement.setString(1,
                    manufacturer.getName());
            updateManufacturerStatement.setString(2,
                    manufacturer.getCountry());
            updateManufacturerStatement.setLong(3,
                    manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can`t update manufacturer in DB to "
                    + manufacturer, ex);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufacturerRequest
                = "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement
                        = connection.prepareStatement(deleteManufacturerRequest)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can`t delete manufacturer from DB with "
                    + id, ex);
        }
    }

    private Manufacturer getDataFromResultSet(ResultSet resultSet) {
        try {
            return new Manufacturer(resultSet.getObject(MANUFACTURER_ID, Long.class),
                    resultSet.getString(MANUFACTURER_NAME),
                    resultSet.getString(MANUFACTURER_COUNTRY));
        } catch (SQLException ex) {
            throw new DataProcessingException("Can`t extract data from result set "
                    + resultSet, ex);
        }
    }
}
