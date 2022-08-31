package mate.jdbc.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest = "INSERT INTO manufacturers(name, country) VALUES(?,?);";
        int NAME_SUBSTITUTION_QUEUE = 1;
        int COUNTRY_SUBSTITUTION_QUEUE = 2;
        try(Connection connection = ConnectionUtil.getDbConnection();
            PreparedStatement createManufactureStatement = connection.prepareStatement(
                            insertManufacturerRequest, Statement.RETURN_GENERATED_KEYS)) {
            createManufactureStatement.setString(NAME_SUBSTITUTION_QUEUE, manufacturer.getName());
            createManufactureStatement.setString(COUNTRY_SUBSTITUTION_QUEUE, manufacturer.getCountry());
            createManufactureStatement.executeUpdate();
            ResultSet generatedKey = createManufactureStatement.getGeneratedKeys();
            if (generatedKey.next()) {
                Long id = generatedKey.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer to DB.", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufactureRequest = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false";
        try(Connection connection = ConnectionUtil.getDbConnection();
            PreparedStatement getManufacturerStatement = connection.prepareStatement(getManufactureRequest)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet manufacturerResult = getManufacturerStatement.executeQuery();
            if (manufacturerResult.next()) {
                return Optional.of(getManufacturer(manufacturerResult));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get a manufacturer by id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String getAllManufacturersRequest = "SELECT * FROM manufacturers WHERE is_deleted = false";
        try(Connection connection = ConnectionUtil.getDbConnection();
        Statement getAllManufacturersStatement = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturersStatement.executeQuery(getAllManufacturersRequest);
            while (resultSet.next()) {
                manufacturers.add(getManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers.", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerRequest = "UPDATE manufacturers SET name = ?, country = ? WHERE id = ?";
        int NAME_SUBSTITUTION_QUEUE = 1;
        int COUNTRY_SUBSTITUTION_QUEUE = 2;
        int ID_SUBSTITUTION_QUEUE = 3;

        try(Connection connection = ConnectionUtil.getDbConnection();
            PreparedStatement updateManufactureStatement = connection.prepareStatement(
                    updateManufacturerRequest, Statement.RETURN_GENERATED_KEYS)) {
            updateManufactureStatement.setString(NAME_SUBSTITUTION_QUEUE, manufacturer.getName());
            updateManufactureStatement.setString(COUNTRY_SUBSTITUTION_QUEUE, manufacturer.getCountry());
            updateManufactureStatement.setLong(ID_SUBSTITUTION_QUEUE, manufacturer.getId());
            updateManufactureStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer with id = " + manufacturer.getId(), e);
        }

        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufacturerRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
        try(Connection connection = ConnectionUtil.getDbConnection();
            PreparedStatement deleteManufactureStatement = connection.prepareStatement(
                    deleteManufacturerRequest, Statement.RETURN_GENERATED_KEYS)) {
            deleteManufactureStatement.setLong(1, id);
            deleteManufactureStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    private static Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getObject(1, Long.class));
        manufacturer.setCountry(resultSet.getString("country"));
        manufacturer.setName(resultSet.getString("name"));
        return manufacturer;
    }
}
