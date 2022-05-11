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
import mate.jdbc.init.ConnectionUtil;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String request = "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't insert manufacturer to DB", ex);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String request = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getManufacturer(resultSet));
            }
            return Optional.empty();
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't get manufacturer from DB by id: " + id, ex);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String request = "SELECT * FROM manufacturers WHERE is_deleted = false";
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(request)) {
            ResultSet resultSet = statement
                    .executeQuery(request);
            while (resultSet.next()) {
                allManufacturers.add(getManufacturer(resultSet));
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't get all manufacturers from DB", ex);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String request =
                "UPDATE manufacturers SET name = ?, country = ? "
                        + "WHERE id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(3, manufacturer.getId());
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't update manufacturer to DB", ex);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String request = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, id);
            return statement.executeUpdate() >= 1;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't delete manufacturer from DB by id: " + id, ex);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(id);
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        return manufacturer;
    }
}
