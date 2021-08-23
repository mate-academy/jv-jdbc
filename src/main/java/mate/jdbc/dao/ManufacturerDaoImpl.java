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
        String insertManufacturerRequest = "INSERT INTO manufacturers(name,country) values(?,?);";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement
                        = connection.prepareStatement(insertManufacturerRequest,
                        Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String query = "SELECT * FROM manufacturers WHERE id = ?"
                + " AND is_deleted = false;";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerByIdStatement =
                        connection.prepareStatement(query)) {
            getManufacturerByIdStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerByIdStatement.executeQuery();
            while (resultSet.next()) {
                return Optional.of(parseManufacturerFromQuery(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by ID:" + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String query = "SELECT * FROM manufacturers WHERE is_deleted = false;";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturersStatement =
                        connection.prepareStatement(query)) {
            ResultSet resultSet = getAllManufacturersStatement.executeQuery();
            while (resultSet.next()) {
                allManufacturers.add(parseManufacturerFromQuery(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET name = ?, country = ? WHERE id = ?"
                + " AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement
                        = connection.prepareStatement(updateRequest)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer by ID: "
                    + manufacturer.getId() + " from DB", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?"
                + " AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement
                        = connection.prepareStatement(deleteRequest,
                        Statement.RETURN_GENERATED_KEYS)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer by ID: " + id
                    + " from DB", e);
        }
    }

    private Manufacturer parseManufacturerFromQuery(ResultSet resultSet) throws SQLException {
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
