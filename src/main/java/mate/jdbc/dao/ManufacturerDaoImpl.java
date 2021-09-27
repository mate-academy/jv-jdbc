package mate.jdbc.dao;

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
        String insertManufacturerQuery = "INSERT INTO manufacturers(name, country) values(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement =
                        connection
                                    .prepareStatement(insertManufacturerQuery,
                        Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException exc) {
            throw new RuntimeException("Can't insert a manufacturer into DB", exc);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturerQuery
                = "SELECT * FROM manufacturers WHERE id = (?) AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement =
                        connection.prepareStatement(getManufacturerQuery)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            Manufacturer manufacturer = null;
            if (resultSet.next()) {
                manufacturer = parseManufacturer(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException exc) {
            throw new RuntimeException("Can't get a manufacturer by id" + id, exc);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String sqlQuery = "SELECT * FROM manufacturers WHERE is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturers = connection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = getAllManufacturers.executeQuery();
            Manufacturer manufacturer = null;
            while (resultSet.next()) {
                manufacturer = parseManufacturer(resultSet);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException exc) {
            throw new RuntimeException("Can't get all manufacturers from DB", exc);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateQuery =
                "UPDATE manufacturers SET name = ?,"
                        + " country = ? WHERE id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement =
                        connection.prepareStatement(updateQuery)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException exc) {
            throw new RuntimeException("Can't softDelete a manufacturer from DB", exc);
        }
    }

    @Override
    public boolean delete(Long id) {
        String softDeleteQuery = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement =
                        connection.prepareStatement(softDeleteQuery)) {
            createManufacturerStatement.setLong(1, id);
            return createManufacturerStatement.executeUpdate() > 0;
        } catch (SQLException exc) {
            throw new RuntimeException("Can't softDelete a manufacturer from DB", exc);
        }
    }

    private Manufacturer parseManufacturer(ResultSet resultSet) throws SQLException{
            String name = resultSet.getString("name");
            Long id = resultSet.getObject("id", Long.class);
            String country = resultSet.getString("country");
            Manufacturer manufacturer = new Manufacturer(id, name, country);
            return manufacturer;
    }
}
