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

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try (Connection dbConnection = ConnectionUtil.getConnection();
                PreparedStatement statement = dbConnection
                        .prepareStatement("INSERT INTO manufacturers (name,country, is_deleted)"
                                        + " VALUES(?,?,?)",
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.setBoolean(3, false);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                Long id = resultSet.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add manufacturer "
                    + manufacturer.toString() + " to DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        try (Connection dbConnection = ConnectionUtil.getConnection();
                PreparedStatement statement = dbConnection
                        .prepareStatement("SELECT * FROM manufacturers"
                                + " WHERE is_deleted = 0 AND id = ?;")) {
            statement.setString(1, String.valueOf(id.toString().charAt(0)));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return manufacturerBuilder(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturerList = new ArrayList<>();
        try (Connection dbConnection = ConnectionUtil.getConnection();
                PreparedStatement statement = dbConnection
                        .prepareStatement("SELECT * FROM manufacturers WHERE is_deleted = 0;")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Optional<Manufacturer> manufacturerOptional = manufacturerBuilder(resultSet);
                if (manufacturerOptional.isEmpty()) {
                    continue;
                }
                manufacturerList.add(manufacturerOptional.get());
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get list of manufacturers", e);
        }
        return manufacturerList;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        try (Connection dbConnection = ConnectionUtil.getConnection();
                PreparedStatement statement = dbConnection
                        .prepareStatement("UPDATE manufacturers SET name = ?,"
                        + " country = ? WHERE id = ? AND is_deleted = 0")) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.setLong(3, manufacturer.getId());
            statement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer: "
                    + manufacturer.toString(), e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection dbConnection = ConnectionUtil.getConnection();
                PreparedStatement statement = dbConnection.prepareStatement("UPDATE manufacturers "
                        + "SET is_deleted = true WHERE id = ?")) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer at id " + id, e);
        }
    }

    private Optional<Manufacturer> manufacturerBuilder(ResultSet resultSet) {
        try {
            Long id = resultSet.getObject("id", Long.class);
            String name = resultSet.getString("name");
            String country = resultSet.getString("country");
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setId(id);
            manufacturer.setName(name);
            manufacturer.setCountry(country);
            return Optional.of(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot create object of Manufacturer class", e);
        }
    }
}
