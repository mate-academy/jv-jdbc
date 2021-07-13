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
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String COUNTRY = "country";
    private String sqlRequest;

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        sqlRequest = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getAllPreparedStatement = connection
                            .prepareStatement(sqlRequest)) {
            ResultSet resultAllSet = getAllPreparedStatement.executeQuery();
            while (resultAllSet.next()) {
                String name = resultAllSet.getString(NAME);
                String country = resultAllSet.getString(COUNTRY);
                Long id = resultAllSet.getObject(ID, Long.class);
                Manufacturer manufacturer = new Manufacturer(name, country);
                manufacturer.setId(id);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get List from DB", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        sqlRequest = "INSERT INTO manufacturers(name,country) VALUES(?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement createPreparedStatement = connection
                         .prepareStatement(sqlRequest, Statement.RETURN_GENERATED_KEYS)) {
            createPreparedStatement.setString(1, manufacturer.getName());
            createPreparedStatement.setString(2, manufacturer.getCountry());
            createPreparedStatement.executeUpdate();
            ResultSet createResultSet = createPreparedStatement.getGeneratedKeys();
            if (createResultSet.next()) {
                Long id = createResultSet.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can`t insert from DB", throwables);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        sqlRequest = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false ;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getByIdPreparedStatement = connection
                            .prepareStatement(sqlRequest)) {
            getByIdPreparedStatement.setLong(1, id);
            ResultSet resultSetGetById = getByIdPreparedStatement.executeQuery();
            if (resultSetGetById.next()) {
                String name = resultSetGetById.getString(NAME);
                String country = resultSetGetById.getString(COUNTRY);
                Long manufacturerId = resultSetGetById.getObject(ID, Long.class);
                Manufacturer manufacturer = new Manufacturer(name, country);
                manufacturer.setId(manufacturerId);
                return Optional.of(manufacturer);
            }
            return Optional.empty();
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can`t get manufacturers from DB by id - "
                    + id + "", throwables);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        sqlRequest = "UPDATE manufacturers SET name = (?), country = (?) "
                + "WHERE id = (?) AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement updatePreparedStatement = connection
                            .prepareStatement(sqlRequest)) {
            updatePreparedStatement.setString(1, manufacturer.getName());
            updatePreparedStatement.setString(2, manufacturer.getCountry());
            updatePreparedStatement.setLong(3, manufacturer.getId());
            updatePreparedStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can`t update DB with manufacturer "
                    + manufacturer + "", throwables);
        }
    }

    @Override
    public boolean delete(Long id) {
        sqlRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement deleteByIdStatement = connection.prepareStatement(sqlRequest)) {
            deleteByIdStatement.setLong(1, id);
            return deleteByIdStatement.executeUpdate() >= 1;
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can`n delete manufacturer by id - "
                    + id + "", throwables);
        }
    }
}
