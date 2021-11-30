package mate.jdbc.dao;

import mate.jdbc.Model.Manufacturer;
import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.util.ConnectionToDbUtil;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao{
    private static final String TABLE_NAME = "manufacturers";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String query = "INSERT INTO " + TABLE_NAME + "(name, country)VALUES(?, ?)";
        try (PreparedStatement statement = ConnectionToDbUtil.getConnection()
                .prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
            return manufacturer;
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can`t write Manufacturer: "
                    + manufacturer.toString() + ", to DB", throwables);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE id = ? AND is_deleted = false";
        try (PreparedStatement statement = ConnectionToDbUtil.getConnection()
                .prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long identification = resultSet.getObject("id", Long.class);
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                return Optional.of(new Manufacturer(identification, name, country));
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can`t get the data from DB with ID: " + id, throwables);
        }
        return Optional.empty();
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String query = "UPDATE " + TABLE_NAME + " SET name = ?, country = ? WHERE id = ? AND is_deleted = false";
        try (PreparedStatement statement = ConnectionToDbUtil.getConnection().prepareStatement(query)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.setLong(3, manufacturer.getId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can`t update data with this Manufacturer: " + manufacturer.toString(), throwables);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE " + TABLE_NAME + " SET is_deleted = true WHERE id = ?";
        try (PreparedStatement statement = ConnectionToDbUtil.getConnection().prepareStatement(query)) {
            statement.setLong(1, id);
            return statement.executeUpdate() >= 1;
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can`t delete manufacturer with ID: " + id, throwables);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE is_deleted = FALSE";
        List<Manufacturer> result = new ArrayList<>();

        try (Statement statement = ConnectionToDbUtil.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Long id = resultSet.getObject("id", Long.class);
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                result.add(new Manufacturer(id, name, country));
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can`t get all data from DB", throwables);
        }
        return result;
    }
}
