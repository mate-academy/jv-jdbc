package dao;

import exceptions.DataProcessingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.lib.Dao;
import model.Manufacturer;
import util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest = "INSERT INTO manufacturers(name, country) values(?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement createStatement =
                         connection.prepareStatement(
                                 insertManufacturerRequest, Statement.RETURN_GENERATED_KEYS)) {
            createStatement.setString(1, manufacturer.getName());
            createStatement.setString(2, manufacturer.getCountry());
            ResultSet generatedKeys = createStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
            createStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer to DB " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturerRequest = "SELECT * FROM manufacturers WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getStatement =
                         connection.prepareStatement(getManufacturerRequest)) {
            getStatement.setLong(1, id);
            ResultSet resultSet = getStatement.executeQuery();
            if (resultSet.next()) {
                Long idOfManufacturer = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                return Optional.of(new Manufacturer(idOfManufacturer, name, country));
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't get  manufacturer from DB with ID: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() throws DataProcessingException {
        List<Manufacturer> manufacturerList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                 Statement getAllStatement = connection.createStatement()) {
            ResultSet resultSet = getAllStatement
                    .executeQuery("SELECT * FROM manufacturers WHERE is_deleted = false");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Long id = resultSet.getObject("id", Long.class);
                Manufacturer manufacturer = new Manufacturer(id, name, country);
                manufacturerList.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
        return manufacturerList;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true where id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement deleteStatement =
                         connection.prepareStatement(
                                 deleteRequest, Statement.RETURN_GENERATED_KEYS)) {
            deleteStatement.setLong(1, id);
            deleteStatement.executeUpdate();
            return deleteStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete user by ID: " + id, e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET name = ?, country = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement updateStatement =
                        connection.prepareStatement(
                                updateRequest, Statement.RETURN_GENERATED_KEYS)) {
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.setLong(3, manufacturer.getId());
            updateStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update Manufacturer with ID: "
                    + manufacturer.getId(), e);
        }
    }
}
