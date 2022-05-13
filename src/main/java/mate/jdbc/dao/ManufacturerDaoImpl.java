package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacture) {
        String insertFormatsStatement = "INSERT INTO manufacturers.name(name,country) VALUES(?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createFormatsStatement = connection.prepareStatement(
                        insertFormatsStatement, Statement.RETURN_GENERATED_KEYS)) {
            createFormatsStatement.setString(1, manufacture.getName());
            createFormatsStatement.setString(2, manufacture.getCountry());
            createFormatsStatement.executeUpdate();
            ResultSet generatedKeys = createFormatsStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacture.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("can't insert format to db", e);
        }
        return manufacture;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String selectByIdQuery =
                "SELECT * FROM manufacturers.name WHERE is_deleted = FALSE AND id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement =
                        connection.prepareStatement(selectByIdQuery)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.getManufacturer(resultSet);
            return Optional.of(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("can't get data from database on this id: " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> data = new ArrayList<>();
        String getAllFormats = "SELECT * FROM manufacturers.name WHERE is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllFormatsStatement = connection.createStatement()) {
            ResultSet resultSet = getAllFormatsStatement.executeQuery(getAllFormats);
            while (resultSet.next()) {
                String nameColumn = resultSet.getString("name");
                String countryColumn = resultSet.getString("country");
                Long id = resultSet.getObject("id", Long.class);
                Manufacturer manufacture = new Manufacturer();
                manufacture.setId(id);
                manufacture.setCountry(countryColumn);
                manufacture.setName(nameColumn);
                data.add(manufacture);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all data from DB", e);
        }
        return data;
    }

    @Override
    public Manufacturer update(Manufacturer manufacture) {
        String updateQuery = "UPDATE manufacturers.name SET name = ?, country = ? WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement = connection.prepareStatement(
                        updateQuery, Statement.NO_GENERATED_KEYS)) {
            updateStatement.setLong(3, manufacture.getId());
            updateStatement.setString(1, manufacture.getName());
            updateStatement.setString(2, manufacture.getCountry());
            updateStatement.executeUpdate();
            return manufacture;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer: " + manufacture, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers.name SET is_deleted = TRUE WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteFormatsStatement = connection
                        .prepareStatement(deleteRequest, Statement.NO_GENERATED_KEYS)) {
            deleteFormatsStatement.setLong(1, id);
            return deleteFormatsStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer with id: " + id, e);
        }
    }
}
