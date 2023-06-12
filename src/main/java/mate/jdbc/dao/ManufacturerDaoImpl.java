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
import mate.jdbc.service.DataProcessingException;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertSqlRequest = "INSERT INTO manufacturers (name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement = connection
                        .prepareStatement(insertSqlRequest, Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t insert manufacturer "
                    + manufacturer + " to DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturerRequest =
                "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement = connection
                        .prepareStatement(getManufacturerRequest)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            while (resultSet.next()) {
                Manufacturer manufacturer = createEntity(resultSet);
                return Optional.of(manufacturer);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get manufacturer by id = " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String getAllRequest = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturersStatement
                        = connection.prepareStatement(getAllRequest)) {
            ResultSet resultSet = getAllManufacturersStatement.executeQuery(getAllRequest);
            while (resultSet.next()) {
                Manufacturer manufacturer = createEntity(resultSet);
                manufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all manufacturers from DB", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateSqlRequest = "UPDATE manufacturers SET name = ?, country = ? "
                + "WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement =
                        connection.prepareStatement(updateSqlRequest)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setString(3, String.valueOf(manufacturer.getId()));
            updateManufacturerStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update manufacturer "
                    + manufacturer + " from DB", e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteSqlRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement =
                        connection.prepareStatement(deleteSqlRequest)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete manufacturer by id "
                    + id + " from DB", e);
        }
    }

    private static Manufacturer createEntity(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(id);
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        return manufacturer;
    }
}
