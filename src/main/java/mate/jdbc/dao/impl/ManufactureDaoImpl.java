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
    public Manufacturer create(Manufacturer o) {
        String creationQuery = "INSERT INTO manufacturers(name, country)"
                + " VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement creationStatement = connection.prepareStatement(creationQuery,
                    Statement.RETURN_GENERATED_KEYS);
            creationStatement.setString(1, o.getName());
            creationStatement.setString(2, o.getCountry());
            creationStatement.executeUpdate();
            ResultSet generatedKey = creationStatement.getGeneratedKeys();
            if (generatedKey.next()) {
                Long id = generatedKey.getLong(1);
                o.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add database manufacturer with filed "
                    + o, e);
        }
        return o;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Manufacturer manufacturer = null;
        String getQuery = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement getStatement = connection.prepareStatement(getQuery);
            getStatement.setLong(1, id);
            ResultSet queryResult = getStatement.executeQuery();
            if (queryResult.next()) {
                manufacturer = manufacturerAssembly(queryResult);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get info about manufacturer with id = "
                            + id, e);
        }
        return Optional.ofNullable(manufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String getAllQuery = "SELECT * FROM manufacturers WHERE is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement getAllStatement = connection.prepareStatement(getAllQuery);
            ResultSet queryResult = getAllStatement.executeQuery();
            while (queryResult.next()) {
                manufacturers.add(manufacturerAssembly(queryResult));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't extract data from database", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer o) {
        String updatingQuery = "UPDATE manufacturers SET name = ?, "
                + "country = ? WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement updatingStatement = connection.prepareStatement(updatingQuery);
            updatingStatement.setString(1, o.getName());
            updatingStatement.setString(2, o.getCountry());
            updatingStatement.setLong(3, o.getId());
            updatingStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer with id = "
                    + o.getId(), e);
        }
        return o;
    }

    @Override
    public boolean delete(Long id) {
        String deletionQuery = "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement deletionStatement = connection.prepareStatement(deletionQuery);
            deletionStatement.setLong(1, id);
            return deletionStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer with id = "
                    + id, e);
        }
    }

    private Manufacturer manufacturerAssembly(ResultSet resultSet) throws SQLException {
        return new Manufacturer(resultSet.getString(COLUMN_NAME),
                resultSet.getString(COLUMN_COUNTRY),
                resultSet.getLong(COLUMN_ID));
    }
}
