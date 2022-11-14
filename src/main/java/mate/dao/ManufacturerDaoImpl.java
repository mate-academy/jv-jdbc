package mate.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.lib.Dao;
import mate.model.Manufacturer;
import mate.util.ConnectionUtil;
import mate.util.DataProcessingException;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    public Manufacturer create(Manufacturer manufacturer) {
        String query = "INSERT INTO manufacturers(name, country) VALUES(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                manufacturer.setId(resultSet.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Cannot add manufacturer. Params: "
                            + "name=" + manufacturer.getName()
                            + ", country=" + manufacturer.getCountry(), e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String query = "SELECT * FROM manufacturers WHERE is_deleted = false AND id = (?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(createManufacturerInstance(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot get info. Params: id=" + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        String query = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            List<Manufacturer> manufacturerList = new ArrayList<>();
            while (resultSet.next()) {
                manufacturerList.add(createManufacturerInstance(resultSet));
            }
            return manufacturerList;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot get all info", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String query =
                "UPDATE manufacturers SET name = (?), country = (?)"
                        + " WHERE id = (?) AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.setLong(3, manufacturer.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot update. "
                    + "Params: manufacturer=" + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE manufacturers SET is_deleted = true WHERE id = ? and is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot delete manufacturer. Params: id=" + id, e);
        }
    }

    private static Manufacturer createManufacturerInstance(ResultSet resultSet) {
        Manufacturer manufacturer = new Manufacturer();
        try {
            manufacturer.setId(resultSet.getObject(1, Long.class));
            manufacturer.setName(resultSet.getString(2));
            manufacturer.setCountry(resultSet.getString(3));
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot create new manufacturer", e);
        }
    }
}
