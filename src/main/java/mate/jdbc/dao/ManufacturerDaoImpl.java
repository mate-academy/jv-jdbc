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
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertQuery = "INSERT INTO manufacturers (name, country)"
                + " VALUES (?, ?)";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createStatement = connection.prepareStatement(
                        insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            createStatement.setString(1, manufacturer.getName());
            createStatement.setString(2, manufacturer.getCountry());
            createStatement.executeUpdate();
            ResultSet generatedKeys = createStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert new manufacture to DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String selectQuery = "SELECT * FROM manufacturers WHERE id = ? "
                + "AND is_deleted <> true;";
        Manufacturer manufacturer = null;

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getStatement = connection.prepareStatement(selectQuery)) {
            getStatement.setLong(1, id);
            ResultSet resultSet = getStatement.executeQuery();

            if (resultSet.next()) {
                manufacturer = parseResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't read from DB ", e);
        }

        return Optional.ofNullable(manufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String selectQuery = "SELECT * FROM manufacturers WHERE is_deleted <> true";

        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllManufacturers = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturers.executeQuery(selectQuery);

            while (resultSet.next()) {
                Manufacturer newManufacturer = parseResultSet(resultSet);
                manufacturers.add(newManufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't execute get all", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateQuery = "UPDATE manufacturers SET name = ?, "
                + "country = ? WHERE id = ?;";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement update = connection.prepareStatement(updateQuery)) {
            update.setLong(3, manufacturer.getId());
            update.setString(1, manufacturer.getName());
            update.setString(2, manufacturer.getCountry());
            update.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update record with id: "
                     + manufacturer.getId(), e);
        }

        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true "
                + "WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteStatement = connection.prepareStatement(deleteRequest)) {
            deleteStatement.setLong(1, id);
            return deleteStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete record with id: " + id, e);
        }
    }

    private Manufacturer parseResultSet(ResultSet resultSet) {
        try {
            Long id = resultSet.getObject("id", Long.class);
            String name = resultSet.getString("name");
            String country = resultSet.getString("country");
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setId(id);
            manufacturer.setName(name);
            manufacturer.setCountry(country);
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't make manufacturer from result set", e);
        }
    }
}
