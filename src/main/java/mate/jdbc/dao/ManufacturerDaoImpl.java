package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.exception.ProcessException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacture) {
        String insertManufacturerRequest = "INSERT INTO taxi_service(name,country) values(?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufactureStatement =
                        connection.prepareStatement(insertManufacturerRequest,
                             Statement.RETURN_GENERATED_KEYS)) {
            createManufactureStatement.setString(1, manufacture.getName());
            createManufactureStatement.setString(2,manufacture.getCountry());
            createManufactureStatement.executeUpdate();
            ResultSet generatedKeys = createManufactureStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacture.setId(id);
            }
        } catch (SQLException e) {
            throw new ProcessException("Can't insert manufacture " + manufacture, e);
        }
        return manufacture;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getRequest = "SELECT * taxi_service WHERE id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufactureStatement =
                        connection.prepareStatement(getRequest)) {
            getManufactureStatement.setLong(1, id);
            ResultSet resultSet = getManufactureStatement.executeQuery();
            Manufacturer manufacture = null;
            if (resultSet.next()) {
                manufacture = getManufacture(resultSet);
            }
            return Optional.ofNullable(manufacture);
        } catch (SQLException e) {
            throw new ProcessException("Can't get information from DB " + id,e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String select = "SELECT * FROM taxi_service WHERE is_deleted = false";
        List<Manufacturer> manufactures = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllStatement = connection.prepareStatement(select)) {
            ResultSet resultSet = getAllStatement.executeQuery();
            while (resultSet.next()) {
                manufactures.add(getManufacture(resultSet));
            }
        } catch (SQLException e) {
            throw new ProcessException("Can't get all information ",e);
        }
        return manufactures;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE taxi_service SET name = ?, country = ? "
                + "WHERE is_deleted = FALSE AND id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufactureStatement =
                        connection.prepareStatement(updateRequest)) {
            updateManufactureStatement.setString(1,manufacturer.getName());
            updateManufactureStatement.setString(2,manufacturer.getCountry());
            updateManufactureStatement.setLong(3,manufacturer.getId());
            updateManufactureStatement.executeUpdate();

        } catch (SQLException e) {
            throw new ProcessException("Can't update manufacture " + manufacturer,e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleted = "UPDATE taxi_service SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement deletedManufactureStatement =
                        connection.prepareStatement(deleted)) {
            deletedManufactureStatement.setLong(1, id);
            return deletedManufactureStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new ProcessException("Can't get deleted information from DB " + id,e);
        }
    }

    public Manufacturer getManufacture(ResultSet resultSet) throws SQLException {
        Manufacturer manufacture = new Manufacturer();
        manufacture.setId(resultSet.getObject("id", Long.class));
        manufacture.setName(resultSet.getString("name"));
        manufacture.setCountry(resultSet.getString("country"));
        return manufacture;
    }
}
