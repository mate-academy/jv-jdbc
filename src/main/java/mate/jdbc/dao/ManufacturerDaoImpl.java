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

    @Override
    public Optional<Manufacturer> get(Long id) {
        Manufacturer manufacturer = null;
        String queryGet =
                "SELECT * FROM manufacturers where id = " + id + " AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getByIdPreparedStatement = connection.prepareStatement(
                        queryGet, Statement.RETURN_GENERATED_KEYS)) {
            ResultSet resultSet = getByIdPreparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                manufacturer = new Manufacturer();
                manufacturer.setName(name);
                manufacturer.setCountry(country);
                manufacturer.setId(id);
            }
        } catch (SQLException throwable) {
            throw new RuntimeException("Could not get a manufacturer by id = " + id, throwable);
        }
        assert manufacturer != null;
        return Optional.of(manufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturerList = new ArrayList<>();
        String getAll = "SELECT * FROM manufacturers where is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllNamesAndCountryStatement = connection.prepareStatement(
                        getAll, Statement.RETURN_GENERATED_KEYS)) {
            ResultSet resultSet = getAllNamesAndCountryStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Long id = resultSet.getObject("id", Long.class);
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setName(name);
                manufacturer.setCountry(country);
                manufacturer.setId(id);
                manufacturerList.add(manufacturer);
            }
            return manufacturerList;
        } catch (SQLException throwable) {
            throw new RuntimeException(" is not good connection method getAll ", throwable);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        Long id = manufacturer.getId();
        String updateQuery = "SELECT * FROM manufacturers where id = " + manufacturer.getId()
                + " AND is_deleted = FALSE;";
        try (Connection connect = ConnectionUtil.getConnection();) {
            Statement statement = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = statement.executeQuery(updateQuery);
            while (resultSet.next()) {
                resultSet.updateString(2, manufacturer.getName());
                resultSet.updateRow();
                resultSet.updateString(3, manufacturer.getCountry());
                resultSet.updateRow();
            }
        } catch (SQLException throwable) {
            throw new RuntimeException(" is not good connection in method update ", throwable);
        }
        return manufacturer;
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest
                = "INSERT INTO manufacturers(name, country) values(?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createNameAndCountry = connection.prepareStatement(
                        insertManufacturerRequest, Statement.RETURN_GENERATED_KEYS)) {
            createNameAndCountry.setString(1, manufacturer.getName());
            createNameAndCountry.setString(2, manufacturer.getCountry());
            createNameAndCountry.executeUpdate();
            ResultSet resultSet = createNameAndCountry.getGeneratedKeys();
            if (resultSet.next()) {
                Long id = resultSet.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't insert formats to DB", e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = TRUE where id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createFormatsStatement = connection.prepareStatement(
                        deleteRequest, Statement.RETURN_GENERATED_KEYS)) {
            createFormatsStatement.setLong(1, id);
            return createFormatsStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new RuntimeException("Can't insert formats to DB", e);
        }
    }
}
