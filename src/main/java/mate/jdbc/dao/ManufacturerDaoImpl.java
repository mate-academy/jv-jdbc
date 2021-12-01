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
        String insertManufactureRequest = "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                        PreparedStatement preparedStatement =
                                connection.prepareStatement(insertManufactureRequest,
                                        Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacture to DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Manufacturer manufacturer = new Manufacturer();
        String getByIdRequest = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                        PreparedStatement preparedStatement =
                                connection.prepareStatement(getByIdRequest)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                manufacturer.setId(id);
                manufacturer.setName(resultSet.getString("name"));
                manufacturer.setCountry(resultSet.getString("country"));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacture with id "
                    + id + " from DB", e);
        }
        return Optional.ofNullable(manufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String getAllRequest = "SELECT * FROM manufacturers";
        try (Connection connection = ConnectionUtil.getConnection();
                        PreparedStatement preparedStatement =
                                connection.prepareStatement(getAllRequest)) {
            ResultSet resultSet = preparedStatement.executeQuery(getAllRequest);
            while (resultSet.next()) {
                Manufacturer manufacturer = new Manufacturer();
                String manufacturerCountry = resultSet.getString("country");
                String manufacturerName = resultSet.getString("name");
                Long id = resultSet.getObject("id", Long.class);
                manufacturer.setCountry(manufacturerCountry);
                manufacturer.setName(manufacturerName);
                manufacturer.setId(id);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all Manufacturers from DB", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET country = ?,"
                + " name = ? WHERE id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                        PreparedStatement preparedStatement =
                                connection.prepareStatement(updateRequest)) {
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.setLong(3, manufacturer.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer from DB", e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                        PreparedStatement deleteStatement =
                                connection.prepareStatement(deleteRequest)) {
            deleteStatement.setLong(1, id);
            deleteStatement.executeUpdate();
            return deleteStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer from DB", e);
        }
    }
}
