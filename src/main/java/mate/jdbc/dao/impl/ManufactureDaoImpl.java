package mate.jdbc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufactureDao;
import mate.jdbc.exceptions.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufactureDaoImpl implements ManufactureDao {
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_COUNTRY = "country";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String query = "INSERT INTO manufacturers(name, country)"
                + " VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                    PreparedStatement creationStatement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)) {
            creationStatement.setString(1, manufacturer.getName());
            creationStatement.setString(2, manufacturer.getCountry());
            creationStatement.executeUpdate();
            ResultSet generatedKey = creationStatement.getGeneratedKeys();
            if (generatedKey.next()) {
                Long id = generatedKey.getLong(1);
                manufacturer.setId(id);
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add database manufacturer with filed "
                    + manufacturer, e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Object id) {
        String query = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getStatement = connection.prepareStatement(query)) {
            getStatement.setLong(1, (Long) id);
            ResultSet queryResult = getStatement.executeQuery();
            Manufacturer manufacturer = null;
            if (queryResult.next()) {
                manufacturer = getManufacturer(queryResult);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get info about manufacturer with id = "
                    + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String query = "SELECT * FROM manufacturers WHERE is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getAllStatement = connection.prepareStatement(query)) {
            ResultSet queryResult = getAllStatement.executeQuery();
            List<Manufacturer> manufacturers = new ArrayList<>();
            while (queryResult.next()) {
                manufacturers.add(getManufacturer(queryResult));
            }
            return manufacturers;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't extract data from database", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String query = "UPDATE manufacturers SET name = ?, "
                + "country = ? WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updatingStatement = connection.prepareStatement(query)) {
            updatingStatement.setString(1, manufacturer.getName());
            updatingStatement.setString(2, manufacturer.getCountry());
            updatingStatement.setLong(3, manufacturer.getId());
            updatingStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer with id = "
                    + manufacturer.getId(), e);
        }
    }

    @Override
    public boolean delete(Object id) {
        String query = "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deletionStatement = connection.prepareStatement(query)) {
            deletionStatement.setLong(1, (Long) id);
            return deletionStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer with id = "
                    + id, e);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        return new Manufacturer(resultSet.getString(COLUMN_NAME),
                resultSet.getString(COLUMN_COUNTRY),
                resultSet.getLong(COLUMN_ID));
    }
}
