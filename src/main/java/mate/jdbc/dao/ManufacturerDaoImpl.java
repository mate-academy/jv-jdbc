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
    public Manufacturer create(Manufacturer manufacturer) {
        String createManufacturerQuery = "INSERT INTO manufacturers"
                + "(manufacturer_name, manufacturer_country) VALUES(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement = connection
                        .prepareStatement(createManufacturerQuery,
                                Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKey = createManufacturerStatement.getGeneratedKeys();

            if (generatedKey.next()) {
                manufacturer.setId(generatedKey
                        .getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer "
                    + "with such params: name = "
                    + manufacturer.getName() + ", country = "
                    + manufacturer.getCountry(), e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturerQuery = "SELECT * FROM manufacturers "
                + "WHERE manufacturer_id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement = connection
                        .prepareStatement(getManufacturerQuery)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet dataFromDB = getManufacturerStatement.executeQuery();

            if (dataFromDB.next()) {
                return Optional.of(createAndInitializeManufacturer(dataFromDB));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't select data with"
                    + " current ID from DB. ID = " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturersList = new ArrayList<>();
        String getAllManufacturersQuery = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllManufacturersStatement = connection.createStatement()) {
            ResultSet dataFromDB = getAllManufacturersStatement
                    .executeQuery(getAllManufacturersQuery);
            while (dataFromDB.next()) {
                manufacturersList.add(createAndInitializeManufacturer(dataFromDB));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't select all data from DB", e);
        }
        return manufacturersList;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerQuery = "UPDATE manufacturers SET manufacturer_name = ?, "
                + "manufacturer_country = ? WHERE manufacturer_id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement = connection
                        .prepareStatement(updateManufacturerQuery,
                                Statement.RETURN_GENERATED_KEYS)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setObject(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Can't update data into "
                    + "current DB with such ID: "
                    + manufacturer.getId(), e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufactureQuery = "UPDATE manufacturers "
                + "SET is_deleted = true WHERE manufacturer_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement = connection
                        .prepareStatement(deleteManufactureQuery)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete manufacturer "
                    + "from DB by such ID: " + id, e);
        }
    }

    private Manufacturer createAndInitializeManufacturer(ResultSet dataFromDB) {
        Manufacturer currentManufacturer;
        try {
            currentManufacturer = new Manufacturer(dataFromDB
                    .getString("manufacturer_name"), dataFromDB.getString("manufacturer_country"));
            currentManufacturer.setId(dataFromDB.getObject("manufacturer_id", Long.class));
        } catch (SQLException e) {
            throw new RuntimeException("Can't initialize manufacturer by data from DB.", e);
        }
        return currentManufacturer;
    }
}
