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
        String createQuery = "INSERT INTO manufacturers(manufacturer_name, manufacturer_country) "
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
        Optional<Manufacturer> optional = Optional.empty();
        String selectQuery = "SELECT * FROM manufacturers "
                + "WHERE manufacturer_id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getManufacturerStatement = connection
                         .prepareStatement(selectQuery)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("manufacturer_name");
                String country = resultSet.getString("manufacturer_country");
                Manufacturer manufacturer = new Manufacturer(id, name, country);
                optional = Optional.of(manufacturer);
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't get manufacturer by id " + id, throwables);
        }
        return optional;
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> list = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                 Statement getAllManufacturerStatement = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturerStatement
                    .executeQuery("SELECT * FROM manufacturers WHERE is_deleted = FALSE");
            while (resultSet.next()) {
                Long id = resultSet.getObject("manufacturer_id", Long.class);
                String name = resultSet.getString("manufacturer_name");
                String country = resultSet.getString("manufacturer_country");
                Manufacturer manufacturer = new Manufacturer(id, name, country);
                list.add(manufacturer);
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't get all data from DB", throwables);
        }
        return list;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateQuery = "UPDATE manufacturers SET manufacturer_name = ?,"
                + " manufacturer_country = ? WHERE manufacturer_id = ? AND is_deleted = FALSE";
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
        String deleteQuery = "UPDATE manufacturers SET is_deleted = 1 WHERE manufacturer_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement = connection
                        .prepareStatement(deleteQuery)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() == 1;
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't delete manufacturer by id " + id, throwables);
        }
    }
}
