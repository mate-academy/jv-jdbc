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
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertFormat = "INSERT INTO manufacturers(name,country) values(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(insertFormat,
                             Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer " + manufacturer, e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getRequest = "SELECT * FROM manufacturers WHERE is_deleted = false AND id =" + id;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement
                        = connection.prepareStatement(getRequest)) {
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            Manufacturer manufacturer = null;
            if (resultSet.next()) {
                manufacturer = getManufacturer(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find manufacturer from DB with this id: "
                    + id + " ", e);
        }

    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllRequest = "SELECT * FROM manufacturers WHERE is_deleted = false";
        List<Manufacturer> allManufacturer = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturersStatement
                        = connection.prepareStatement(getAllRequest)
        ) {
            ResultSet resultSet = getAllManufacturersStatement
                    .executeQuery();
            while (resultSet.next()) {
                allManufacturer.add(getManufacturer(resultSet));
            }
            return allManufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB ", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET name = ?, country = ?"
                + " WHERE is_deleted = false AND id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(updateRequest)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.setLong(3, manufacturer.getId());
            statement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturers from DB by id: "
                    + manufacturer.getId(), e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(deleteRequest)) {
            statement.setLong(1, id);
            return statement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete this manufacturer"
                    + " from DB with this id: " + id + " ", e);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        return new Manufacturer(id, name, country);
    }
}
