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
        String insertStatement = "INSERT INTO manufacturers(country, name) VALUES(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createNewManufacturerStatement = 
                        connection.prepareStatement(insertStatement, 
                        Statement.RETURN_GENERATED_KEYS)) {
            createNewManufacturerStatement.setString(1, manufacturer.getCountry());
            createNewManufacturerStatement.setString(2, manufacturer.getName());
            createNewManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createNewManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create new manufacturer: " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String insertStatement = "SELECT * FROM manufacturers WHERE id = ?";
        Optional<Manufacturer> manufacturer = Optional.empty();

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement = 
                        connection.prepareStatement(insertStatement)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet manufacturerSet = getManufacturerStatement.executeQuery();
            if (manufacturerSet.next() && !manufacturerSet.getBoolean("is_deleted")) {
                manufacturer = Optional.of(extractManufacturer(manufacturerSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer from DB. Id: " + id, e);
        }
        return manufacturer;
    }

    @Override
    public List<Manufacturer> getAll() {
        String insertStatement = "SELECT * FROM manufacturers";
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturerStatement = 
                        connection.prepareStatement(insertStatement)) {
            ResultSet manufacturerSet = getAllManufacturerStatement.executeQuery();
            while (manufacturerSet.next()) {
                if (!manufacturerSet.getBoolean("is_deleted")) {
                    manufacturers.add(extractManufacturer(manufacturerSet));
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get a list of manufacturers from DB", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String insertStatement = "UPDATE manufacturers SET name = ?, country = ? WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement = 
                        connection.prepareStatement(insertStatement)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer: " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Boolean delete(Long id) {
        String insertStatement = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement = 
                        connection.prepareStatement(insertStatement)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer. Id: " + id, e);
        }
    }

    private Manufacturer extractManufacturer(ResultSet set) throws SQLException {
        return new Manufacturer(set.getObject(1, Long.class), 
                set.getString("name"), 
                set.getString("country"));
    }
}
