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
            throw new DataProcessingException("Can`t put insert manufacturer to db ", e);
        }
        return manufacture;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getRequest =
                "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = FALSE";
        Manufacturer manufacturer = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getFormatsStatement =
                        connection.prepareStatement(getRequest)) {
            getFormatsStatement.setLong(1,id);
            ResultSet resultSet = getFormatsStatement.executeQuery();
            if (resultSet.next()) {
                manufacturer = getManufacturer(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get manufacturer from db ", e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String getAllReuarments = "SELECT * FROM manufacturers WHERE is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllFormatsStatement =
                        connection.prepareStatement(getAllReuarments)) {
            ResultSet resultSet = getAllFormatsStatement
                    .executeQuery(getAllReuarments);
            while (resultSet.next()) {
                Manufacturer manufacture = getManufacturer(resultSet);
                manufacturers.add(manufacture);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all manufacturers from db ", e);
        }
        return manufacturers;
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
            throw new DataProcessingException("Can't update manufacturer in db", e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createFormatsStatement =
                        connection.prepareStatement(deleteRequest,
                                Statement.RETURN_GENERATED_KEYS)) {
            createFormatsStatement.setLong(1, id);
            return createFormatsStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete insert manufacturer in db ", e);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) {
        Manufacturer manufacturer = new Manufacturer();
        try {
            manufacturer.setId(resultSet.getObject(1,Long.class));
            manufacturer.setName(resultSet.getString("name"));
            manufacturer.setCountry(resultSet.getString("country"));
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all manufacturer from db ", e);
        }
        return manufacturer;
    }
}
