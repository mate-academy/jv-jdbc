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
                "SELECT * FROM manufacturers WHERE id = " + id + " AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getByIdPreparedStatement = connection.prepareStatement(
                        queryGet, Statement.RETURN_GENERATED_KEYS)) {
            ResultSet resultSet = getByIdPreparedStatement.executeQuery();
            if (resultSet.next()) {
                manufacturer = parseManufacturer(resultSet);
            }
        } catch (SQLException throwable) {
            throw new RuntimeException("Could not get a manufacturer by id = " + id, throwable);
        }
        return Optional.ofNullable(manufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {
        Manufacturer manufacturer;
        List<Manufacturer> manufacturerList = new ArrayList<>();
        String getAll = "SELECT * FROM manufacturers WHERE is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllNamesAndCountryStatement = connection.prepareStatement(
                        getAll, Statement.RETURN_GENERATED_KEYS)) {
            ResultSet resultSet = getAllNamesAndCountryStatement.executeQuery();
            while (resultSet.next()) {
                manufacturer = parseManufacturer(resultSet);
                manufacturerList.add(manufacturer);
            }
            return manufacturerList;
        } catch (SQLException throwable) {
            throw new RuntimeException(" is not good connection method getAll ", throwable);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String query = "UPDATE manufacturers SET name = ?, country = ? WHERE id = ?";
        try (Connection connect = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connect.prepareStatement(query)) {
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.setLong(3, manufacturer.getId());
            preparedStatement.executeUpdate();
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

    private Manufacturer parseManufacturer(ResultSet resultSe) {
        Manufacturer manufacturer = new Manufacturer();
        try {
            manufacturer.setName(resultSe.getString("name"));
            manufacturer.setCountry(resultSe.getString("country"));
            manufacturer.setId(resultSe.getObject("id", Long.class));
        } catch (SQLException e) {
            throw new RuntimeException("Can't create manufacturer object in resultSe", e);
        }
        return manufacturer;
    }
}
