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
        String query = "INSERT INTO manufacturers(name, country) VALUES (?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturersStatements
                        = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            getAllManufacturersStatements.setString(2, manufacturer.getName());
            getAllManufacturersStatements.setString(3, manufacturer.getCountry());
            getAllManufacturersStatements.executeUpdate();
            ResultSet generatedKeys = getAllManufacturersStatements.getGeneratedKeys();
            generatedKeys.next();
            manufacturer.setId(generatedKeys.getObject("id", Long.class));
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create manufacturer. Id = "
                    + manufacturer.getId(), e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String query = "SELECT * FROM manufacturers WHERE is_deleted = FALSE AND id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatements = connection.prepareStatement(query)) {
            getManufacturerStatements.setLong(1, id);
            ResultSet resultSet = getManufacturerStatements.executeQuery();
            resultSet.next();
            String name = resultSet.getString("name");
            String country = resultSet.getString("country");
            Manufacturer manufacturer = new Manufacturer(id, name, country);
            return Optional.of(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer. Id = " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> result = new ArrayList<>();
        String query = "SELECT * FROM manufacturers WHERE is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturersStatements
                        = connection.prepareStatement(query)) {
            ResultSet resultSet = getAllManufacturersStatements.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getObject("id", Long.class);
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Manufacturer manufacturer = new Manufacturer(id, name, country);
                result.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers", e);
        }
        return result;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String query = "UPDATE manufacturers SET name = ?, country = ? "
                + "WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturersStatements
                        = connection.prepareStatement(query)) {
            getAllManufacturersStatements.setString(1, manufacturer.getName());
            getAllManufacturersStatements.setString(2, manufacturer.getCountry());
            getAllManufacturersStatements.setLong(3, manufacturer.getId());
            getAllManufacturersStatements.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer. Id = "
                    + manufacturer.getId(), e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturersStatements
                        = connection.prepareStatement(query)) {
            getAllManufacturersStatements.setLong(1, id);
            return getAllManufacturersStatements.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer. Id = " + id, e);
        }
    }
}
