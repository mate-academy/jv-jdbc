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

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertRequest = "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturersStatement =
                        connection.prepareStatement(insertRequest,
                                Statement.RETURN_GENERATED_KEYS)) {
            createManufacturersStatement.setString(1, manufacturer.getName());
            createManufacturersStatement.setString(2, manufacturer.getCountry());
            createManufacturersStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturersStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create " + manufacturer
                    + " entity in DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Manufacturer manufacturer = null;
        String getRequest = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement =
                        connection.prepareStatement(getRequest)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            if (resultSet.next()) {
                manufacturer = getManufacturerFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer from DB by id: " + id, e);
        }
        return Optional.ofNullable(manufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturersData = new ArrayList<>();
        String getAllRequest = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllManufacturersStatement = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturersStatement.executeQuery(getAllRequest);
            while (resultSet.next()) {
                Manufacturer manufacturer = getManufacturerFromResultSet(resultSet);
                manufacturersData.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
        return manufacturersData;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET name = ?, country = ? "
                + "WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement =
                        connection.prepareStatement(updateRequest)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update " + manufacturer + " entity in DB", e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        boolean isSuccessful;
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturersStatement =
                        connection.prepareStatement(deleteRequest)) {
            deleteManufacturersStatement.setLong(1, id);
            isSuccessful = deleteManufacturersStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete entity from DB by id" + id, e);
        }
        return isSuccessful;
    }

    private Manufacturer getManufacturerFromResultSet(ResultSet resultSet) {
        Long id;
        String name;
        String country;
        try {
            id = resultSet.getObject("id", Long.class);
            name = resultSet.getString("name");
            country = resultSet.getString("country");
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer entity from result set", e);
        }
        return new Manufacturer(id, name, country);
    }
}
