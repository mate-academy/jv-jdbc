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

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String sqlCode = "INSERT INTO taxi_db.manufacturers(name, country) "
                + "VALUES(?, ?);";
        try (
                Connection connection = connectionUtil.getConnection();
                PreparedStatement createStatement = connection.prepareStatement(sqlCode)
        ) {
            createStatement.setString(1, manufacturer.getName());
            createStatement.setString(2, manufacturer.getCountry());
            createStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("cant add new manufacturer to DB", e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String sqlCode = "SELECT * FROM taxi_db.manufacturers WHERE id = ? AND is_deleted = FALSE;";
        try (
                Connection connection = connectionUtil.getConnection();
                PreparedStatement getByIdStatement = connection.prepareStatement(sqlCode)
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
        String sqlCode = "SELECT * FROM taxi_db.manufacturers WHERE is_deleted = FALSE";
        List<Manufacturer> result = new ArrayList<>();
        try (
                Connection connection = connectionUtil.getConnection();
                Statement getAllStatement = connection.createStatement()
        ) {
            ResultSet resultSet = getAllStatement.executeQuery(sqlCode);
            while (resultSet.next()) {
                if (!resultSet.getBoolean("is_deleted")) {
                    result.add(parseManufacturer(resultSet));
                }
            }
            return result;
        } catch (SQLException e) {
            throw new DataProcessingException("cant create getAllStatement", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String sqlCode = "UPDATE manufacturers "
                + "SET name = ?, country = ?"
                + "WHERE id = ? AND is_deleted = FALSE";
        try (
                Connection connection = connectionUtil.getConnection();
                PreparedStatement updateStatement = connection.prepareStatement(sqlCode)
        ) {
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.setLong(3, manufacturer.getId());
            updateStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "cant update manufacturer " + manufacturer.toString(), e
            );
        }
    }

    @Override
    public boolean delete(Long id) {
        String sqlCode = "UPDATE taxi_db.manufacturers SET is_deleted = true WHERE id = ?";
        try (
                Connection connection = connectionUtil.getConnection();
                PreparedStatement deleteStatement =
                        connection.prepareStatement(sqlCode, Statement.RETURN_GENERATED_KEYS)
        ) {
            deleteStatement.setLong(1, id);
            return deleteStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("cant delete manufacturer with id " + id, e);
        }
    }
}
