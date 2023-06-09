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
import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final String INSERT = "INSERT INTO manufacturers(name,country) VALUES(?,?);";
    private static final String SELECT_BY_ID = "SELECT * FROM manufacturers WHERE id = ? "
            + "AND is_deleted = FALSE";
    private static final String SELECT_ALL = "SELECT * FROM manufacturers WHERE is_deleted = FALSE";
    private static final String UPDATE = "UPDATE manufacturers SET name = ?, country = ? "
            + "WHERE id = ? AND is_deleted = FALSE;";
    private static final String DELETE = "UPDATE manufacturers SET is_deleted = TRUE"
            + " WHERE id = ? AND is_deleted = FALSE;";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement creteStatement = connection.prepareStatement(INSERT,
                         Statement.RETURN_GENERATED_KEYS)) {
            creteStatement.setString(1, manufacturer.getName());
            creteStatement.setString(2, manufacturer.getCountry());
            creteStatement.executeUpdate();
            ResultSet keys = creteStatement.getGeneratedKeys();
            while (keys.next()) {
                Long id = keys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacture" + manufacturer, e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getStatement = connection.prepareStatement(SELECT_BY_ID)) {
            getStatement.setLong(1,id);
            ResultSet resultSet = getStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(createManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't select manufacture from id = " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> listAllManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getAllStatement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = getAllStatement.executeQuery();
            while (resultSet.next()) {
                listAllManufacturers.add(createManufacturer(resultSet));
            }
            return listAllManufacturers;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't select all manufactures", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement updateStatement = connection.prepareStatement(UPDATE)) {
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2,manufacturer.getCountry());
            updateStatement.setLong(3,manufacturer.getId());
            updateStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacture:"
                    + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteStatement = connection.prepareStatement(DELETE)) {
            deleteStatement.setLong(1, id);
            return deleteStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacture from id = " + id, e);
        }
    }

    private Manufacturer createManufacturer(ResultSet resultSet) throws SQLException {
        return new Manufacturer(resultSet.getObject("id", Long.class),
                resultSet.getString("name"), resultSet.getString("country"));
    }
}
