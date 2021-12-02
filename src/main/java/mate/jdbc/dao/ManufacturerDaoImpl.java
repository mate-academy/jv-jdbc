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
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public List<Manufacturer> getAll() {
        String getAllManufacturersRequest = "SELECT * FROM manufacturers "
                        + "WHERE is_deleted != 1;";
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturers
                        = connection.prepareStatement(getAllManufacturersRequest)) {
            ResultSet resultSet =
                    getAllManufacturers.executeQuery();
            while (resultSet.next()) {
                Manufacturer manufacturer = parseManufacture(resultSet);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
        return allManufacturers;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturersRequest =
                "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = 0;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturersStatement =
                        connection.prepareStatement(getManufacturersRequest);) {
            getManufacturersStatement.setLong(1, id);
            ResultSet resultSet = getManufacturersStatement.executeQuery();
            Manufacturer manufacturer = null;
            if (resultSet.next()) {
                manufacturer = parseManufacture(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by id: "
                    + id + " from DB", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerRequest = "UPDATE manufacturers SET name = ?,country = ? "
                + "WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement =
                        connection.prepareStatement(
                                updateManufacturerRequest, Statement.RETURN_GENERATED_KEYS)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer: "
                    + manufacturer.toString() + " from DB", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufacturerRequest = "UPDATE manufacturers "
                + "SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement =
                        connection.prepareStatement(
                                deleteManufacturerRequest, Statement.RETURN_GENERATED_KEYS)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer by id: "
                    + id + "from DB", e);
        }
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String createManufacturerRequest =
                "INSERT INTO manufacturers(name, country, is_deleted) VALUES(?, ?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement =
                        connection.prepareStatement(
                                createManufacturerRequest, Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.setString(3, "0");
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer: "
                    + manufacturer.toString() + " to DB", e);
        }
        return manufacturer;
    }

    private Manufacturer parseManufacture(ResultSet resultSet) {
        try {
            Long id = resultSet.getObject("id", Long.class);
            String name = resultSet.getString("name");
            String country = resultSet.getString("country");
            Manufacturer manufacturer = new Manufacturer(id, name, country);
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer from ResultSet: "
                    + resultSet, e);
        }
    }
}
