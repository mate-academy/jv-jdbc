package mate.jdbc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    public static final String ID_COLUMN = "id";
    public static final String NANE_COLUMN = "name";
    public static final String COUNTRY_COLUMN = "country";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String createRequest = "INSERT INTO manufacturers (name, country) VALUES(?, ?);";
        try (Connection dbConnection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement =
                        dbConnection.prepareStatement(createRequest,
                                Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                manufacturer.setId(generatedKeys.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create such manufacturer: "
                    + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getByIdRequest = "SELECT * FROM manufacturers WHERE is_deleted = FALSE AND id = ?;";
        try (Connection dbConnection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement =
                        dbConnection.prepareStatement(getByIdRequest)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet manufacturersResultSet =
                    getManufacturerStatement.executeQuery();
            if (manufacturersResultSet.next()) {
                return Optional.of(createManufacturer(manufacturersResultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String getAllRequest = "SELECT * FROM manufacturers WHERE is_deleted = FALSE;";
        try (Connection dbConnection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturersStatement =
                        dbConnection.prepareStatement(getAllRequest)) {
            ResultSet manufacturersResultSet =
                    getAllManufacturersStatement.executeQuery();
            while (manufacturersResultSet.next()) {
                allManufacturers.add(createManufacturer(manufacturersResultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all formats from DB", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET name = ?, country = ? "
                + "WHERE id = ? AND is_deleted = FALSE;";
        try (Connection dbConnection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement =
                        dbConnection.prepareStatement(updateRequest)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer with id: "
                    + manufacturer.getId(), e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ?;";
        try (Connection dbConnection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement =
                        dbConnection.prepareStatement(deleteRequest)) {
            deleteManufacturerStatement.setLong(1,id);
            return deleteManufacturerStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer with id: " + id, e);
        }
    }

    private Manufacturer createManufacturer(ResultSet manufacturersResultSet)
            throws SQLException {
        Long id = manufacturersResultSet.getObject(ID_COLUMN, Long.class);
        String name = manufacturersResultSet.getString(NANE_COLUMN);
        String country = manufacturersResultSet.getString(COUNTRY_COLUMN);
        return new Manufacturer(id, name, country);
    }
}
