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
        String request = "INSERT INTO manufacturers(name, country) VALUES(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(request,
                             Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer to DB. "
                    + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String request = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false";
        try (PreparedStatement statement = getPreparedStatement(request)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            manufacturers = getManufacturers(resultSet);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer from DB. ID: " + id, e);
        }
        return manufacturers.size() == 0 ? Optional.empty() : Optional.of(manufacturers.get(0));
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String request = "SELECT * FROM manufacturers WHERE is_deleted = false";
        try (PreparedStatement statement = getPreparedStatement(request)) {
            ResultSet resultSet = statement.executeQuery();
            manufacturers = getManufacturers(resultSet);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        int executeUpdate;
        String request =
                "UPDATE manufacturers SET name = ?, "
                        + "country = ? WHERE id = ? AND is_deleted = false";
        try (PreparedStatement statement = getPreparedStatement(request)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.setLong(3, manufacturer.getId());
            executeUpdate = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer" + manufacturer, e);
        }
        return executeUpdate == 0 ? create(manufacturer) : manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String request = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (PreparedStatement statement = getPreparedStatement(request)) {
            statement.setLong(1, id);
            return statement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer from DB. ID: " + id, e);
        }
    }

    private PreparedStatement getPreparedStatement(String statement) throws SQLException {
        Connection connection = ConnectionUtil.getConnection();
        return connection.prepareStatement(statement);
    }

    private List<Manufacturer> getManufacturers(ResultSet resultSet) throws SQLException {
        List<Manufacturer> manufacturers = new ArrayList<>();
        while (resultSet.next()) {
            Long id = resultSet.getObject("id", Long.class);
            String name = resultSet.getString("name");
            String country = resultSet.getString("country");
            manufacturers.add(Manufacturer.of(id, name, country));
        }
        return manufacturers;
    }
}
