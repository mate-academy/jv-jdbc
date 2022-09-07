package mate.jdbc.dao;

import mate.jdbc.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufactureRequest = "INSERT INTO manufacturers(name, country) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement createManufacturerStatement =
                     connection.prepareStatement(insertManufactureRequest, Statement.RETURN_GENERATED_KEYS)){
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturers to DB", e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getByIdRequest = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement getManufacturerStatement =
                     connection.prepareStatement(getByIdRequest, Statement.RETURN_GENERATED_KEYS)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(
                        getManufacturerFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find manufacturer by id = " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String getAllRequest = "SELECT * FROM manufacturers WHERE is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
             Statement getAllManufacturersStatement = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturersStatement.executeQuery(getAllRequest);
            while (resultSet.next()){
                Manufacturer manufacturer = getManufacturerFromResultSet(resultSet);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
        return null;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufactureRequest = "UPDATE manufacturers " +
                "SET name = ?, country = ? " +
                "WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement updateManufacturerStatement =
                     connection.prepareStatement(updateManufactureRequest)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer: " + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufactureRequest = "UPDATE manufacturers " +
                "SET is_deleted = TRUE" +
                "WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement deleteManufacturerStatement =
                     connection.prepareStatement(deleteManufactureRequest)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer by id = " + id, e);
        }
    }

    private Manufacturer getManufacturerFromResultSet(ResultSet resultSet) {
        Manufacturer manufacturer = new Manufacturer();
        try {
            Long manufacturerId = resultSet.getLong("id");
            String manufacturerName = resultSet.getString("name");
            String manufacturerCountry = resultSet.getString("country");
            manufacturer.setId(manufacturerId);
            manufacturer.setName(manufacturerName);
            manufacturer.setCountry(manufacturerCountry);
        } catch (SQLException e) {
            throw new RuntimeException("Can`t get manufacturer from ResultSet", e);
        }
        return manufacturer;
    }
}
