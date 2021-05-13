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
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String createQuery = "INSERT INTO manufacturers(name, country) "
                + "values(?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement insertManufacturerStatement = connection
                        .prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)) {
            insertManufacturerStatement.setString(1, manufacturer.getName());
            insertManufacturerStatement.setString(2, manufacturer.getCountry());
            insertManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = insertManufacturerStatement.getGeneratedKeys();
            while (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't create new manufacturer into DB. Manufacturer "
                    + manufacturer, throwables);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String selectQuery = "SELECT * FROM manufacturers "
                + "WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getManufacturerStatement = connection
                         .prepareStatement(selectQuery)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getManufacturer(resultSet));
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't get manufacturer by id " + id, throwables);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> list = new ArrayList<>();
        String slelectQuery = "SELECT * FROM manufacturers WHERE is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                 Statement getAllManufacturerStatement = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturerStatement.executeQuery(slelectQuery);
            while (resultSet.next()) {
                list.add(getManufacturer(resultSet));
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't get all data from DB", throwables);
        }
        return list;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateQuery = "UPDATE manufacturers SET name = ?,"
                + " country = ? WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement = connection
                        .prepareStatement(updateQuery)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't update manufacturer by id "
                    + manufacturer.getId(), throwables);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteQuery = "UPDATE manufacturers SET is_deleted = 1 WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement = connection
                        .prepareStatement(deleteQuery)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() == 1;
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't delete manufacturer by id " + id, throwables);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getObject("id", Long.class));
        manufacturer.setName(resultSet.getString("name"));
        manufacturer.setCountry(resultSet.getString("country"));
        return manufacturer;
    }
}
