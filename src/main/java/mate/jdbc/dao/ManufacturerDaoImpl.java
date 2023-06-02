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
import mate.jdbc.models.Manufacturer;
import mate.jdbc.service.ParseManufacturerResultSet;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static ParseManufacturerResultSet parseManufacturerResultSet;
    private static final String insertManufacturerRequest
            = "INSERT INTO manufacturers (name, country) VALUES (?, ?)";
    private static final String selectManufacturerById
            = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted IS NOT TRUE";
    private static final String getAllManufacturers
            = "SELECT * FROM manufacturers WHERE is_deleted IS NOT TRUE";
    private static final String updateManufacturer
            = "UPDATE manufacturers SET name = ?, country = ?, is_deleted = FALSE WHERE id = ?";
    private static final String deleteRequest
            = "UPDATE manufacturers SET is_deleted  = true where id = ?";

    public ManufacturerDaoImpl() {
        parseManufacturerResultSet = new ParseManufacturerResultSet();
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturersStatement
                        = connection.prepareStatement(insertManufacturerRequest,
                        Statement.RETURN_GENERATED_KEYS)) {
            createManufacturersStatement.setString(1, manufacturer.getName());
            createManufacturersStatement.setString(2, manufacturer.getCountry());
            createManufacturersStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturersStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long id = generatedKeys.getObject(1, long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerById =
                        connection.prepareStatement(selectManufacturerById)) {
            getManufacturerById.setLong(1, id);
            try (ResultSet resultSet = getManufacturerById.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(parseManufacturerResultSet.parseResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllManufacturersStatements = connection.createStatement();
                ResultSet resultSet = getAllManufacturersStatements
                        .executeQuery(getAllManufacturers)) {
            while (resultSet.next()) {
                allManufacturers.add(parseManufacturerResultSet.parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from the database", e);
        }

        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement updateManufacturersStatement =
                        connection.prepareStatement(updateManufacturer)) {
            updateManufacturersStatement.setString(1, manufacturer.getName());
            updateManufacturersStatement.setString(2, manufacturer.getCountry());
            updateManufacturersStatement.setLong(3, manufacturer.getId());
            int rowsUpdated = updateManufacturersStatement.executeUpdate();
            if (rowsUpdated > 0) {
                return manufacturer;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer: " + manufacturer, e);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturersStatement
                        = connection.prepareStatement(deleteRequest,
                        Statement.RETURN_GENERATED_KEYS)) {
            deleteManufacturersStatement.setLong(1, id);
            return deleteManufacturersStatement.executeUpdate() >= 1;

        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer, id: " + id, e);
        }
    }

}
