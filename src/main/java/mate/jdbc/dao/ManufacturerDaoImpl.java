package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.db.Manufacturer;
import mate.jdbc.exeption.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.util.connection.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacture) {
        String createRearmament = "INSERT INTO manufacturers(name, country) values(?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createFormatsStatement =
                        connection.prepareStatement(createRearmament,
                                Statement.RETURN_GENERATED_KEYS)) {
            createFormatsStatement.setString(1, manufacture.getName());
            createFormatsStatement.setString(2, manufacture.getCountry());
            createFormatsStatement.executeUpdate();
            ResultSet generatedKeys = createFormatsStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacture.setId(id);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can`t put insert formats to db ", e);
        }
        return manufacture;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getRequest =
                "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false";
        Manufacturer manufacturer = new Manufacturer();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getFormatsStatement =
                        connection.prepareStatement(getRequest)) {
            getFormatsStatement.setLong(1,id);
            ResultSet resultSet = getFormatsStatement.executeQuery();
            if (resultSet.next()) {
                manufacturer = getManufacture(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get formats from db ", e);
        }
        return Optional.ofNullable(manufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> formats = new ArrayList<>();
        String getAllReuarments = "SELECT * FROM manufacturers where is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllFormatsStatement = connection.createStatement()) {
            ResultSet resultSet = getAllFormatsStatement
                    .executeQuery(getAllReuarments);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String county = resultSet.getString("country");
                Long id = resultSet.getObject("id",Long.class);
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(id);
                manufacturer.setName(name);
                manufacturer.setCountry(county);
                formats.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all formats from db ", e);
        }
        return formats;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateReuarmets = "UPDATE manufacturers SET name = ?, country = ?"
                + "WHERE is_deleted = FALSE AND id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufactureStatement
                        = connection.prepareStatement(updateReuarmets)) {
            updateManufactureStatement.setString(1,manufacturer.getName());
            updateManufactureStatement.setString(2,manufacturer.getCountry());
            updateManufactureStatement.setLong(3,manufacturer.getId());
            updateManufactureStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update format in db", e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true where id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createFormatsStatement =
                        connection.prepareStatement(deleteRequest,
                                Statement.RETURN_GENERATED_KEYS)) {
            createFormatsStatement.setLong(1, id);
            return createFormatsStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete insert formats in db ", e);
        }
    }

    private Manufacturer getManufacture(ResultSet resultSet) {
        Manufacturer manufacturer = new Manufacturer();
        try {
            manufacturer.setId(resultSet.getObject(1,Long.class));
            manufacturer.setName(resultSet.getString("name"));
            manufacturer.setCountry(resultSet.getString("country"));
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all format from db ", e);
        }
        return manufacturer;
    }
}
