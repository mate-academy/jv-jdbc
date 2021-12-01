package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.exception.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertQuery = "INSERT INTO manufacturers(name,country) VALUES(?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createPreparedStatement = connection
                        .prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            createPreparedStatement.setString(1, manufacturer.getName());
            createPreparedStatement.setString(2, manufacturer.getCountry());
            createPreparedStatement.executeUpdate();
            ResultSet createResultSet = createPreparedStatement.getGeneratedKeys();
            if (createResultSet.next()) {
                Long id = createResultSet.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can`t insert to DB " + manufacturer, throwables);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getQuery = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getByIdPreparedStatement = connection
                        .prepareStatement(getQuery)) {
            getByIdPreparedStatement.setLong(1, id);
            ResultSet resultSet = getByIdPreparedStatement.executeQuery();
            Manufacturer manufacturer = null;
            if (resultSet.next()) {
                manufacturer = parseResultSet(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get manufacturers from DB by id - "
                    + id, e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateQuery = "UPDATE manufacturers SET name = (?), country = (?) "
                + "WHERE id = (?) AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updatePreparedStatement = connection
                        .prepareStatement(updateQuery)) {
            updatePreparedStatement.setString(1, manufacturer.getName());
            updatePreparedStatement.setString(2, manufacturer.getCountry());
            updatePreparedStatement.setLong(3, manufacturer.getId());
            updatePreparedStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can`t update DB with manufacturer "
                    + manufacturer, throwables);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteQuery = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteByIdStatement = connection
                        .prepareStatement(deleteQuery)) {
            deleteByIdStatement.setLong(1, id);
            return deleteByIdStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can`t delete manufacturer by id - "
                    + id, throwables);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String getAllQuery = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllPreparedStatement = connection
                        .prepareStatement(getAllQuery)) {
            ResultSet resultSet = getAllPreparedStatement.executeQuery();
            while (resultSet.next()) {
                allManufacturers.add(parseResultSet(resultSet));
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can`t get all manufactures from DB", throwables);
        }
        return allManufacturers;
    }

    private Manufacturer parseResultSet(ResultSet resultSet) {
        try {
            Long id = resultSet.getObject("id", Long.class);
            String name = resultSet.getString("name");
            String country = resultSet.getString("country");
            return new Manufacturer(id, name, country);
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can`t parse from ResultSet - "
                    + resultSet, throwables);
        }
    }
}
