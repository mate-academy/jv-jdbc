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
import mate.jdbc.init.ConnectionInit;
import mate.jdbc.lib.Dao;
import mate.jdbc.models.Manufacturer;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String COUNTRY = "country";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest = "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (Connection connection = ConnectionInit.getConnection();
                     PreparedStatement createManufacturerStatement
                            = connection.prepareStatement(insertManufacturerRequest,
                            Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1,
                    manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create manufacturer from DB with :"
                    + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getRequest = "SELECT * from manufacturers where is_deleted = 0 and id = ?;";
        Optional<Manufacturer> optionalManufacturer = null;
        try (Connection connection = ConnectionInit.getConnection();
                PreparedStatement getManufacturerStatement
                        = connection.prepareStatement(getRequest,
                        Statement.RETURN_GENERATED_KEYS)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            if (resultSet.next()) {
                optionalManufacturer = Optional.of(parseResultSetToManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer from DB with id = " + id, e);
        }
        return optionalManufacturer;
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionInit.getConnection();
                Statement getAllStatements = connection.createStatement()) {
            ResultSet resultSet = getAllStatements
                    .executeQuery("SELECT * from manufacturers where is_deleted = 0;");
            while (resultSet.next()) {
                allManufacturers.add(parseResultSetToManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateElementRequest = "UPDATE manufacturers SET name = ?, country = ? "
                + "WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionInit.getConnection();
                     PreparedStatement updateStatement = connection.prepareStatement(
                         updateElementRequest)) {
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.setLong(3, manufacturer.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update data in DB with id = "
                    + manufacturer.getId(), e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufacturerRequest =
                "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionInit.getConnection();
                 PreparedStatement getManufacturerStatement
                         = connection.prepareStatement(deleteManufacturerRequest,
                         Statement.RETURN_GENERATED_KEYS)) {
            getManufacturerStatement.setLong(1, id);
            return getManufacturerStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer from DB with id = " + id,
                    e);
        }
    }

    private Manufacturer parseResultSetToManufacturer(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject(ID, Long.class);
        String name = resultSet.getString(NAME);
        String country = resultSet.getString(COUNTRY);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(id);
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        return manufacturer;
    }
}
