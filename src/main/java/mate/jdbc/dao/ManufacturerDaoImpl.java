package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.exeption.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String createRequest = "INSERT INTO manufacturers "
                + "(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement createManufacturerStatements =
                         connection.prepareStatement(createRequest,
                             Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatements.setString(1, manufacturer.getName());
            createManufacturerStatements.setString(2, manufacturer.getCountry());
            createManufacturerStatements.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatements.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create manufacturer " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String selectRequest = "SELECT name, country "
                + "FROM manufacturers "
                + "WHERE is_deleted = 0 AND id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getManufacturerStatements =
                         connection.prepareStatement(selectRequest)) {
            getManufacturerStatements.setLong(1, id);
            ResultSet resultSet = getManufacturerStatements.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                return Optional.of(getManufacturer(id, name, country));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        try (Connection connection = ConnectionUtil.getConnection();
                 Statement getAllManufacturersStatements =
                         connection.createStatement()) {
            String selectRequest = "SELECT id, name, country "
                    + "FROM manufacturers "
                    + "WHERE is_deleted = 0;";
            ResultSet resultSet = getAllManufacturersStatements.executeQuery(selectRequest);
            List<Manufacturer> manufacturerList = new ArrayList<>();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                manufacturerList.add(getManufacturer(id, name, country));
            }
            return manufacturerList;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't select all manufacturers from DB", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers "
                + "SET name = ?, country = ? "
                + "WHERE id = ? AND is_deleted = 0";
        try (Connection connection = ConnectionUtil.getConnection();
                     PreparedStatement updateManufacturerStatements =
                             connection.prepareStatement(updateRequest)) {
            updateManufacturerStatements.setString(1, manufacturer.getName());
            updateManufacturerStatements.setString(2, manufacturer.getCountry());
            updateManufacturerStatements.setLong(3, manufacturer.getId());
            int rows = updateManufacturerStatements.executeUpdate();
            if (rows != 1) {
                throw new DataProcessingException("Can't update manufacturer "
                        + manufacturer, new SQLException());
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer "
                    + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String updateRequest = "UPDATE manufacturers "
                + "SET is_deleted = 1 "
                + "WHERE id = ? AND is_deleted = 0";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement updateManufacturerStatements =
                         connection.prepareStatement(updateRequest)) {
            updateManufacturerStatements.setLong(1, id);
            int rows = updateManufacturerStatements.executeUpdate();
            if (rows == 1) {
                return true;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer with ID "
                    + id, e);
        }
        return false;

    }

    private Manufacturer getManufacturer(Long id, String name, String country) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(id);
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        return manufacturer;
    }
}
