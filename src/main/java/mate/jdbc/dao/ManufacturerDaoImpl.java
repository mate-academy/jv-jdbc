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
public class ManufacturerDaoImpl implements ManufactureDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String createQuery = "INSERT INTO manufactures(name, country) values (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createStatement =
                        connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)) {
            createStatement.setString(1, manufacturer.getName());
            createStatement.setString(2, manufacturer.getCountry());
            createStatement.executeUpdate();
            ResultSet generatedKeys = createStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create new car" + manufacturer, e);
        }

    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getQuery = "SELECT * FROM manufactures WHERE id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getStatement = connection.prepareStatement(getQuery)) {
            getStatement.setLong(1, id);
            ResultSet resultSet = getStatement.executeQuery();
            Manufacturer manufacturer = null;
            if (resultSet.next()) {
                manufacturer = makeManufacturer(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer" + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufactures = new ArrayList<>();
        String getAllQuery = "SELECT * FROM manufactures WHERE is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllDataFromDB = connection.createStatement()) {
            ResultSet resultSet = getAllDataFromDB.executeQuery(getAllQuery);
            while (resultSet.next()) {
                allManufactures.add(makeManufacturer(resultSet));
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't get all manufactures", throwables);
        }
        return allManufactures;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateQuery = "UPDATE manufactures SET name = ?, country = ?"
                + " WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.setObject(3, manufacturer.getId());
            updateStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update a manufacturer" + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteQuery = "UPDATE manufactures SET is_deleted = true WHERE id =?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteStatement = connection
                        .prepareStatement(deleteQuery, Statement.RETURN_GENERATED_KEYS)) {
            deleteStatement.setLong(1, id);
            return deleteStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete a manufacturer" + id, e);
        }
    }

    private Manufacturer makeManufacturer(ResultSet resultSet) {
        try {
            String name = resultSet.getString("name");
            String country = resultSet.getString("country");
            Long id = resultSet.getObject("id", Long.class);
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setCountry(country);
            manufacturer.setName(name);
            manufacturer.setId(id);
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Not correct data to create manufacturer", e);
        }
    }
}
