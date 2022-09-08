package mate.jdbc.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao{
    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM manufacturers");
            while (resultSet.next()) {
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(resultSet.getInt("id"));
                manufacturer.setName(resultSet.getString("name"));
                manufacturer.setCountry(resultSet.getString("country"));
                manufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t getAll manufacturers from db", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String sqlInsertManufacturerRequest =
                "INSERT INTO manufacturers(name, country) values (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement createManufacturersStatement =
                     connection.prepareStatement(sqlInsertManufacturerRequest,
                             PreparedStatement.RETURN_GENERATED_KEYS)) {
            createManufacturersStatement.setString(1, manufacturer.getName());
            createManufacturersStatement.setString(2, manufacturer.getCountry());
            createManufacturersStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturersStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create manufacturer in DB. "
                    + "Manufacturer: " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String sqlGetManufacturerRequest =
                "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
            PreparedStatement getManufacturedStatement = connection.prepareStatement(sqlGetManufacturerRequest)) {
            getManufacturedStatement.setLong(1, id);
            ResultSet resultSet = getManufacturedStatement.executeQuery();
            Manufacturer manufacturer = null;
            if (resultSet.next()) {
                manufacturer = getManufacturer(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get manufacturer from DB by id:" + id, e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String sqlUpdateManufacturerRequest =
                "UPDATE manufacturers " +
                        "SET name = ?, " +
                        "country = ? " +
                        "where id = ? " +
                        "AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
            PreparedStatement updateManufacturersStatement =
                    connection.prepareStatement(sqlUpdateManufacturerRequest)) {
                updateManufacturersStatement.setString(1, manufacturer.getName());
                updateManufacturersStatement.setString(2, manufacturer.getCountry());
                updateManufacturersStatement.setLong(3, manufacturer.getId());
                updateManufacturersStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer with this id. ID:"
                    + manufacturer.getId(), e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String sqlDeleteManufacturerRequest =
                "UPDATE manufacturers SET is_deleted = true where id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement deleteManufacturersStatement =
                     connection.prepareStatement(sqlDeleteManufacturerRequest)) {
            deleteManufacturersStatement.setLong(1, id);
            return deleteManufacturersStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer from DB. id:"
                    + id, e);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) throws SQLException{
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getObject("id", Long.class));
        manufacturer.setName(resultSet.getString("name"));
        manufacturer.setCountry(resultSet.getString("country"));
        return manufacturer;
    }
}
