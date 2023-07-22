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
import mate.jdbc.lib.DataProcessingException;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String createRequest = "INSERT INTO manufacturers(name, country) VALUES(?, ?);";
        try (Connection dbConnection = ConnectionUtil.getConnection();
                PreparedStatement createStatement = dbConnection
                        .prepareStatement(createRequest,Statement.RETURN_GENERATED_KEYS)) {
            createStatement.setString(1, manufacturer.getName());
            createStatement.setString(2, manufacturer.getCountry());
            createStatement.executeUpdate();
            ResultSet generatedKeys = createStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                manufacturer.setId(generatedKeys.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer " + manufacturer
                    + " to DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getRequest = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = FALSE;";
        try (Connection dbConnection = ConnectionUtil.getConnection();
                PreparedStatement getStatement = dbConnection.prepareStatement(getRequest)) {
            getStatement.setLong(1, id);
            ResultSet manufacturersSet = getStatement.executeQuery();
            if (manufacturersSet.next()) {
                return Optional.of(getManufacturerFromResultSet(manufacturersSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturers by id: " + id
                    + "from DB", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String getAllRequest = "SELECT * FROM manufacturers WHERE is_deleted = FALSE;";
        try (Connection dbConnection = ConnectionUtil.getConnection();
                PreparedStatement getStatement = dbConnection.prepareStatement(getAllRequest)) {
            ResultSet manufacturersSet = getStatement.executeQuery();
            while (manufacturersSet.next()) {
                Manufacturer manufacturer = getManufacturerFromResultSet(manufacturersSet);
                manufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturers from DB", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = FALSE;";
        try (Connection dbConnection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement = dbConnection.prepareStatement(updateRequest)) {
            updateStatement.setLong(1, manufacturer.getId());
            ResultSet manufacturerSet = updateStatement.executeQuery();
            if (manufacturerSet.next()) {
                manufacturer.setName(manufacturerSet.getString("name"));
                manufacturer.setCountry(manufacturerSet.getString("country"));
                return manufacturer;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer " + manufacturer
                    + "from DB", e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = TRUE;";
        try (Connection dbConnection = ConnectionUtil.getConnection();
                PreparedStatement deleteStatement = dbConnection.prepareStatement(deleteRequest)) {
            return deleteStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer by id:" + id
                    + "from DB", e);
        }
    }

    private Manufacturer getManufacturerFromResultSet(ResultSet resultSet) throws SQLException {
        return new Manufacturer(resultSet.getObject("id", Long.class),
                resultSet.getString("name"),
                resultSet.getString("country"));
    }
}
