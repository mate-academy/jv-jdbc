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
        String insertManufacturerRequest = "INSERT INTO manufacturers(name, country) VALUES(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement = connection
                         .prepareStatement(insertManufacturerRequest,
                                 Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException throwable) {
            throw new DataProcessingException("Can't get insert new manufacturer "
                    + manufacturer.getName() + " to DB", throwable);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getByIdRequest = "SELECT * FROM manufacturers "
                + "WHERE is_deleted=false AND id=?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getManufacturerStatement = connection
                         .prepareStatement(getByIdRequest)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(constructManufacturer(resultSet));
            }
            return Optional.empty();
        } catch (SQLException throwable) {
            throw new DataProcessingException("Can't get get manufacturer with id "
                    + id + " from DB", throwable);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllRequest = "SELECT * FROM manufacturers WHERE is_deleted=false;";
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                 Statement getAllInformationStatement = connection.createStatement()) {
            ResultSet resultSet = getAllInformationStatement.executeQuery(getAllRequest);
            while (resultSet.next()) {
                allManufacturers.add(constructManufacturer(resultSet));
            }
        } catch (SQLException throwable) {
            throw new DataProcessingException("Can't get all manufacturers", throwable);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET name=?, country =? "
                + "WHERE id=? AND is_deleted=false;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement updateManufacturerStatement = connection
                         .prepareStatement(updateRequest)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
        } catch (SQLException throwable) {
            throw new DataProcessingException("Can't update manufacturer with id "
                    + manufacturer.getId() + " in DB", throwable);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true WHERE  id=?";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement deleteManufacturerStatement = connection
                         .prepareStatement(deleteRequest)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() > 0;
        } catch (SQLException throwable) {
            throw new DataProcessingException("Can't get delete manufacturer "
                    + id + " from DB", throwable);
        }
    }

    private Manufacturer constructManufacturer(ResultSet resultSet) {
        try {
            Manufacturer manufacturer = new Manufacturer(resultSet.getString("name"),
                    resultSet.getString("country"));
            manufacturer.setId(resultSet.getObject("id", Long.class));
            return manufacturer;
        } catch (SQLException throwable) {
            throw new DataProcessingException("Can't construct manufacturer", throwable);
        }
    }
}
