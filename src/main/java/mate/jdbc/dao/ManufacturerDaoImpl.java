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
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final String createManufacturerQuery
            = "INSERT INTO manufacturers(name, country) " + "VALUES(?, ?);";
    private static final String selectManufacturerQuery
            = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false;";
    private static final String selectAllManufacturersQuery
            = "SELECT * FROM manufacturers WHERE is_deleted = false;";
    private static final String updateManufacturerQuery
            = "UPDATE manufacturers SET name = ?, country = ? "
            + "WHERE id = ? AND is_deleted = false;";
    private static final String deleteManufacturerQuery
            = "UPDATE manufacturers SET is_deleted = true WHERE id = ? AND is_deleted = false";
    private static final String deleteAllManufacturerQuery = "DELETE FROM manufacturers";
    private static final int ID_INDEX = 1;
    private static final int NAME_INDEX = 2;
    private static final int COUNTRY_INDEX = 3;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement = connection.prepareStatement(
                        createManufacturerQuery, Statement.RETURN_GENERATED_KEYS)
        ) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(ID_INDEX, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataProcessingException("Cannot create manufacturer: " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement = connection.prepareStatement(
                        selectManufacturerQuery, Statement.RETURN_GENERATED_KEYS)
        ) {
            getManufacturerStatement.setLong(1, id);
            ResultSet generatedKeys = getManufacturerStatement.executeQuery();
            if (generatedKeys.next()) {
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(generatedKeys.getObject(ID_INDEX, Long.class));
                manufacturer.setName(generatedKeys.getString(NAME_INDEX));
                manufacturer.setCountry(generatedKeys.getString(COUNTRY_INDEX));
                return Optional.of(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot get manufacturer by id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> list = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturersStatement = connection.prepareStatement(
                        selectAllManufacturersQuery);
                ResultSet generatedKeys = getAllManufacturersStatement.executeQuery()
        ) {

            while (generatedKeys.next()) {
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(generatedKeys.getObject(ID_INDEX, Long.class));
                manufacturer.setName(generatedKeys.getString(NAME_INDEX));
                manufacturer.setCountry(generatedKeys.getString(COUNTRY_INDEX));
                list.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot get manufacturers", e);
        }
        return list;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement = connection.prepareStatement(
                        updateManufacturerQuery, Statement.RETURN_GENERATED_KEYS)
        ) {
            updateManufacturerStatement.setString(ID_INDEX, manufacturer.getName());
            updateManufacturerStatement.setString(NAME_INDEX, manufacturer.getCountry());
            updateManufacturerStatement.setLong(COUNTRY_INDEX, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot update manufacturer: " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement = connection.prepareStatement(
                        deleteManufacturerQuery, Statement.RETURN_GENERATED_KEYS)
        ) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot delete manufacturer by id: " + id, e);
        }
    }

    @Override
    public boolean deleteAll() { // for testing
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteAllManufacturerStatement = connection.prepareStatement(
                        deleteAllManufacturerQuery, Statement.RETURN_GENERATED_KEYS)
        ) {
            return deleteAllManufacturerStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot delete manufacturers", e);
        }
    }
}
