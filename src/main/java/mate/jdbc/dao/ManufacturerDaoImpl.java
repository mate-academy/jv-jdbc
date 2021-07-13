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
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_COUNTRY = "country";
    public static final String COLUMN_ID = "id";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String request = "INSERT INTO manufacturers(name, country) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(request,
                        Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            setKey(manufacturer, generatedKeys);
        } catch (SQLException e) {
            throw new RuntimeException("Can't operate with DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String request = "SELECT * FROM manufacturers where id= ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(request)) {
            preparedStatement.setObject(1, id);
            ResultSet generatedKeys = preparedStatement.executeQuery();
            if (generatedKeys.next()) {
                String name = generatedKeys.getString(COLUMN_NAME);
                String country = generatedKeys.getString(COLUMN_COUNTRY);
                return Optional.of(new Manufacturer(id, name, country));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get manufacturer by id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers;
        String request = "SELECT * FROM manufacturers WHERE is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement
                    .executeQuery(request);
            manufacturers = getManufacturers(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException("Can't operate with DB", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String request = "UPDATE manufacturers "
                + "SET name = ?, country = ? "
                + "WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement query = connection.prepareStatement(request)) {
            query.setString(1, manufacturer.getName());
            query.setString(2, manufacturer.getCountry());
            query.setObject(3, manufacturer.getId());
            query.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new RuntimeException("Can't update DB with " + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String request = "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement query = connection.prepareStatement(request)) {
            query.setObject(1, id);
            int updateRows = query.executeUpdate();
            return updateRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete by id " + id, e);
        }
    }

    private List<Manufacturer> getManufacturers(ResultSet resultSet) throws SQLException {
        List<Manufacturer> manufacturerList = new ArrayList<>();
        while (resultSet.next()) {
            String name = resultSet.getString(COLUMN_NAME);
            String country = resultSet.getString(COLUMN_COUNTRY);
            Long id = resultSet.getObject(COLUMN_ID, Long.class);
            manufacturerList.add(new Manufacturer(id, name, country));
        }
        return manufacturerList;
    }

    private void setKey(Manufacturer manufacturer, ResultSet generatedKeys) throws SQLException {
        if (generatedKeys.next()) {
            Long id = generatedKeys.getObject(1, Long.class);
            manufacturer.setId(id);
        }
    }
}
