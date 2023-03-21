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
    private static final String FORMAT_REQUEST_INSERT =
            "INSERT INTO manufacturers(name, country) VALUES(?, ?);";
    private static final String FORMAT_REQUEST_GET =
            "SELECT * FROM manufacturers WHERE is_deleted = FALSE AND id = ?";
    private static final String FORMAT_REQUEST_GET_ALL =
            "SELECT * FROM manufacturers WHERE is_deleted = FALSE;";
    private static final String FORMAT_REQUEST_UPDATE =
            "UPDATE manufacturers SET name = ?, country = ? WHERE id = ? AND is_deleted = FALSE;";
    private static final String FORMAT_REQUEST_DELETE_SOFT =
            "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ? AND is_deleted = FALSE;";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement =
                         connection.prepareStatement(FORMAT_REQUEST_INSERT,
                             Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add to DB: " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement =
                         connection.prepareStatement(FORMAT_REQUEST_GET)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(makeManufacturerFromInput(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement =
                         connection.prepareStatement(FORMAT_REQUEST_GET_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                manufacturers.add(makeManufacturerFromInput(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get list of manufacturers", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement =
                         connection.prepareStatement(FORMAT_REQUEST_UPDATE)) {
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.setLong(3, manufacturer.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update information for " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement =
                         connection.prepareStatement(FORMAT_REQUEST_DELETE_SOFT)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer by id: " + id, e);
        }
    }

    private Manufacturer makeManufacturerFromInput(ResultSet resultSet) throws SQLException {
        Long manufacturerId = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        return new Manufacturer(manufacturerId, name, country);
    }
}
