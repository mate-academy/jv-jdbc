package mate.jdbc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufactureDao;
import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.utils.ConnectionUtil;

@Dao
public class ManufactureDaoImpl implements ManufactureDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String query = "INSERT INTO manufacturers(name, country) "
                + "values(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                            PreparedStatement statement = connection.prepareStatement(query,
                                            Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.executeUpdate();
            ResultSet key = statement.getGeneratedKeys();
            while (key.next()) {
                Long id = key.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't set values to DB " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String query = "SELECT * FROM manufacturers "
                + "WHERE id = (?) AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return Optional.of(getManufacture(result));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get by id", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        String query = "SELECT * FROM manufacturers "
                + "WHERE is_deleted = FALSE";
        List<Manufacturer> manufacturesList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                         PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                manufacturesList.add(getManufacture(result));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all data from DB!", e);
        }
        return manufacturesList;
    }

    @Override
    public Manufacturer update(Manufacturer manufacture) {
        String query = "UPDATE manufacturers "
                + "SET name = ?, "
                + "country = ? "
                + "WHERE id = ? "
                + "AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                         PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, manufacture.getName());
            statement.setString(2, manufacture.getCountry());
            statement.setLong(3, manufacture.getId());
            statement.executeUpdate();
            return manufacture;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update date in DB!", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE manufacturers "
                + "SET is_deleted = TRUE WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                         PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete date in DB " + id, e);
        }
    }

    private Manufacturer getManufacture(ResultSet result) throws SQLException {
        return new Manufacturer(
                result.getObject("id", Long.class),
                result.getString("name"),
                result.getString("country"));
    }
}
