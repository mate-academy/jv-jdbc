package mate.jdbc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Dao;
import mate.jdbc.lib.DataProcessingException;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private final ConnectionUtil connectionUtil = new ConnectionUtil();

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String createQuery = "INSERT INTO taxi_db.manufacturers(name, country) "
                + "VALUES(?, ?);";
        try (
                Connection connection = connectionUtil.getConnection();
                PreparedStatement createStatement =
                        connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)
        ) {
            createStatement.setString(1, manufacturer.getName());
            createStatement.setString(2, manufacturer.getCountry());
            createStatement.executeUpdate();
            ResultSet keys = createStatement.getGeneratedKeys();
            if (keys.next()) {
                manufacturer.setId(keys.getObject("id", Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("cant add new manufacturer: "
                    + manufacturer + " to DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getQuery = "SELECT * FROM taxi_db.manufacturers "
                + "WHERE id = ? AND is_deleted = FALSE;";
        try (
                Connection connection = connectionUtil.getConnection();
                PreparedStatement getByIdStatement = connection.prepareStatement(getQuery)
        ) {
            getByIdStatement.setLong(1, id);
            getByIdStatement.executeQuery();
            ResultSet resultSet = getByIdStatement.getResultSet();
            if (resultSet.next()) {
                return Optional.of(parseManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("cant get manufacturer with id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllQuery = "SELECT * FROM taxi_db.manufacturers WHERE is_deleted = FALSE";
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (
                Connection connection = connectionUtil.getConnection();
                PreparedStatement getAllStatement = connection.prepareStatement(getAllQuery)
        ) {
            ResultSet resultSet = getAllStatement.executeQuery();
            while (resultSet.next()) {
                manufacturers.add(parseManufacturer(resultSet));
            }
            return manufacturers;
        } catch (SQLException e) {
            throw new DataProcessingException("cant get all manufacturers", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateQuery = "UPDATE manufacturers "
                + "SET name = ?, country = ?"
                + "WHERE id = ? AND is_deleted = FALSE";
        try (
                Connection connection = connectionUtil.getConnection();
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery)
        ) {
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.setLong(3, manufacturer.getId());
            updateStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "cant update manufacturer " + manufacturer, e
            );
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteQuery = "UPDATE taxi_db.manufacturers SET is_deleted = true WHERE id = ?";
        try (
                Connection connection = connectionUtil.getConnection();
                PreparedStatement deleteStatement =
                        connection.prepareStatement(deleteQuery)
        ) {
            deleteStatement.setLong(1, id);
            return deleteStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DataProcessingException("cant delete manufacturer with id " + id, e);
        }
    }

    private Manufacturer parseManufacturer(ResultSet resultSet) {
        try {
            return new Manufacturer(
                    resultSet.getObject("id", Long.class),
                    resultSet.getString("name"),
                    resultSet.getString("country")
            );
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "cant get manufacturer from resultSet" + resultSet, e
            );
        }
    }
}
