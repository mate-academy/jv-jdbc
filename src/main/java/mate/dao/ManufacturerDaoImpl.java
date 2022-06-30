package mate.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.domain.Manufacturer;
import mate.jdbc.lib.Dao;
import mate.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest =
                "INSERT INTO manufacturers(name, country) values(?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement createManufacturerStatement
                         = connection.prepareStatement(
                             insertManufacturerRequest, Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException throwables) {
            throw new RuntimeException("cant insert manufacturer to DB. Manufacturer: "
                    + manufacturer, throwables);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String request = "SELECT * FROM manufacturers WHERE is_deleted = false AND id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturer =
                        connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS)) {
            getManufacturer.setLong(1, id);
            ResultSet generatedKeys = getManufacturer.executeQuery();
            if (generatedKeys.next()) {
                return Optional.ofNullable(getManufacturerFromResultSet(generatedKeys));
            }
        } catch (SQLException throwables) {
            throw new RuntimeException("cant get manufacturers from DB with given id: "
                    + id, throwables);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String getAllQuery = "SELECT * FROM manufacturers WHERE is_deleted IS NOT TRUE";
        try (Connection connection = ConnectionUtil.getConnection();
                     PreparedStatement getAllManufacturersStatement
                         = connection.prepareStatement(getAllQuery);) {
            ResultSet resultSet = getAllManufacturersStatement
                    .executeQuery();
            while (resultSet.next()) {
                allManufacturers.add(getManufacturerFromResultSet(resultSet));
            }
        } catch (SQLException throwables) {
            throw new RuntimeException("cant get all manufacturers from DB", throwables);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest =
                "UPDATE manufacturers SET name = ?, country = ? WHERE "
                        + "is_deleted = false AND id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement updateManufacturerStatement
                         = connection.prepareStatement(updateRequest)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new RuntimeException("cant update manufacturer to DB with: "
                    + manufacturer, throwables);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement
                        = connection.prepareStatement(
                             deleteRequest, Statement.RETURN_GENERATED_KEYS)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() >= 1;
        } catch (SQLException throwables) {
            throw new RuntimeException("cant insert manufacturer to DB, no row for: "
                    + id, throwables);
        }
    }

    private Manufacturer getManufacturerFromResultSet(ResultSet manufacturerRow)
            throws SQLException {
        return new Manufacturer(
                manufacturerRow.getLong(1),
                manufacturerRow.getString(2),
                manufacturerRow.getString(3));
    }
}
