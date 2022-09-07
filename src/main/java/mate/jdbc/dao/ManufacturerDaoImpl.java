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
        String createRequest = "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement
                        = connection.prepareStatement(
                                createRequest, Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't insert new manufacture "
                    + manufacturer, throwables);
        }
        return manufacturer;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET name = ?, country = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement =
                        connection.prepareStatement(updateRequest)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't update manufacturer "
                    + manufacturer, throwables);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Manufacturer manufacturer = new Manufacturer();
        String getByIdRequest = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getByIDStatement =
                        connection.prepareStatement(getByIdRequest)) {
            getByIDStatement.setLong(1, id);
            ResultSet resultSet = getByIDStatement.executeQuery();
            if (resultSet.next()) {
                manufacturer = fillManufacturerFields(resultSet);
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't get manufacturer by id " + id, throwables);
        }
        return Optional.ofNullable(manufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String getAllRequest = "SELECT * FROM manufacturers";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturerStatement =
                        connection.prepareStatement(getAllRequest)) {
            ResultSet resultSet = getAllManufacturerStatement.executeQuery(getAllRequest);
            while (resultSet.next()) {
                allManufacturers.add(fillManufacturerFields(resultSet));
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't return all manufacturers from DB", throwables);
        }
        return allManufacturers;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement =
                        connection.prepareStatement(deleteRequest)) {
            deleteManufacturerStatement.setLong(1, id);
            deleteManufacturerStatement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't delete row by id->" + id, throwables);
        }
    }

    private Manufacturer fillManufacturerFields(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getObject(1, Long.class));
        manufacturer.setName(resultSet.getString("name"));
        manufacturer.setCountry(resultSet.getString("country"));
        return manufacturer;
    }
}
