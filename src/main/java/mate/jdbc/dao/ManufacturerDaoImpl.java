package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.exceptions.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_COUNTRY = "country";

    @Override
    public Manufacturer create(Manufacturer o) {
        String creationQuery = "INSERT INTO manufacturers(name, country)"
                + " VALUES ((?), (?));";
        Manufacturer manufacturer = null;
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement creationStatement = connection.prepareStatement(creationQuery,
                    Statement.RETURN_GENERATED_KEYS);
            creationStatement.setString(1, o.getName());
            creationStatement.setString(2, o.getCountry());
            creationStatement.executeUpdate();
            ResultSet generatedKey = creationStatement.getGeneratedKeys();
            if (generatedKey.next()) {
                Long id = generatedKey.getLong(1);
                manufacturer = new Manufacturer(o.getName(), o.getCountry(), id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add database manufacturer with filed "
                    + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Manufacturer manufacturer = null;
        String getQuery = "SELECT * FROM manufacturers WHERE id = (?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement getStatement = connection.prepareStatement(getQuery);
            getStatement.setLong(1, id);
            ResultSet queryResult = getStatement.executeQuery();
            if (queryResult.next()) {
                manufacturer = new Manufacturer(queryResult.getString(COLUMN_NAME),
                        queryResult.getString(COLUMN_COUNTRY),
                        queryResult.getLong(COLUMN_ID));
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
            ResultSet manufacturersInfo = getAllStatement.executeQuery();
            while (manufacturersInfo.next()) {
                manufacturers.add(new Manufacturer(manufacturersInfo.getString(COLUMN_NAME),
                        manufacturersInfo.getString(COLUMN_COUNTRY),
                        manufacturersInfo.getLong(COLUMN_ID)));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't extract data from database", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer o) {
        String updatingQuery = "UPDATE manufacturers SET name = (?), "
                + "country = (?) WHERE id = (?);";
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
        String deletionQuery = "UPDATE manufacturers SET is_deleted = TRUE WHERE id = (?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement deletionStatement = connection.prepareStatement(deletionQuery);
            deletionStatement.setLong(1, id);
            return deletionStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer with id = "
                    + id, e);
        }
    }
}
