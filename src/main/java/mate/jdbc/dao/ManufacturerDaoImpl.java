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
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

public class ManufacturerDaoImpl implements ManufacturerDao {
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturer = "INSERT INTO manufacturers(name, country) VALUES(?, ?);";
        int returnGeneratedKeys = Statement.RETURN_GENERATED_KEYS;
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement createManufacturerStatement =
                         connection
                                 .prepareStatement(insertManufacturer, returnGeneratedKeys)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet resultSet = createManufacturerStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Long id = resultSet.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer " + manufacturer, e);
        }
        return manufacturer;
    }

    public Optional<Manufacturer> get(Long id) {
        String selectManufacturer =
                "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getManufacturerStatement =
                         connection.prepareStatement(selectManufacturer)) {
            getManufacturerStatement.setLong(1, id);
            getManufacturerStatement.executeQuery();
            ResultSet resultSet = getManufacturerStatement.getResultSet();
            List<Manufacturer> manufacturerList = unpackResultSet(resultSet);
            if (!manufacturerList.isEmpty()) {
                return Optional.of(manufacturerList.get(0));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by id=" + id, e);
        }
        return Optional.empty();
    }

    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers;
        String selectAllManufacturers =
                "SELECT * FROM manufacturers WHERE is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getAllManufacturersStatement = connection
                         .prepareStatement(selectAllManufacturers)) {
            ResultSet resultSet = getAllManufacturersStatement.executeQuery();
            allManufacturers = unpackResultSet(resultSet);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all the data from DB", e);
        }
        return allManufacturers;
    }

    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturer =
                "UPDATE manufacturers SET name = ?, country = ? "
                        + "WHERE id = ? and is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement updateManufacturerStatement = connection
                         .prepareStatement(updateManufacturer)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            int i = updateManufacturerStatement.executeUpdate();
            if (i > 0) {
                return manufacturer;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer " + manufacturer, e);
        }
        return null;
    }

    public boolean delete(Long id) {
        String deleteManufacturer =
                "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getManufacturerStatement =
                         connection.prepareStatement(deleteManufacturer)) {
            getManufacturerStatement.setLong(1, id);
            return getManufacturerStatement.executeUpdate() >= 1;

        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer by id=" + id, e);
        }

    }

    private List<Manufacturer> unpackResultSet(ResultSet resultSet) {
        List<Manufacturer> manufacturersList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Long id = resultSet.getObject("id", Long.class);
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Manufacturer manufacturer = new Manufacturer(id, name, country);
                manufacturersList.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't unpack ResultSet", e);
        }
        return manufacturersList;
    }
}
