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
        String insertFormatRequest = "INSERT INTO manufacturers (name, country) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createStatement =
                        connection.prepareStatement(
                                insertFormatRequest,
                                Statement.RETURN_GENERATED_KEYS)) {
            createStatement.setString(1, manufacturer.getName());
            createStatement.setString(2, manufacturer.getCountry());
            createStatement.executeUpdate();
            ResultSet generatedKeys = createStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer to DataBase", e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getRequestById = "SELECT * FROM manufacturers WHERE is_deleted = FALSE AND id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerByIdStatement
                        = connection.prepareStatement(getRequestById)) {
            getManufacturerByIdStatement.setLong(1, id);
            ResultSet manufacturerDataById = getManufacturerByIdStatement.executeQuery();
            if (manufacturerDataById.next()) {
                return Optional.of(createManufacturerInstance(manufacturerDataById));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String getAllQuery = "SELECT * FROM manufacturers "
                + "WHERE is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturesStatement
                        = connection.prepareStatement(getAllQuery)) {
            ResultSet manufacturersFromDB = getAllManufacturesStatement.executeQuery();
            while (manufacturersFromDB.next()) {
                allManufacturers.add(createManufacturerInstance(manufacturersFromDB));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer from Data Base.", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturer = "UPDATE manufacturers SET name = ?, country = ? "
                + "WHERE is_deleted = FALSE AND id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement
                        = connection.prepareStatement(updateManufacturer)) {
            updateStatement.setLong(3, manufacturer.getId());
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer by id "
                    + manufacturer.getId(), e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufacturer = "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement
                        = connection.prepareStatement(deleteManufacturer)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete data be id " + id, e);
        }
    }

    private Manufacturer createManufacturerInstance(ResultSet manufacturerSet) throws SQLException {
        Long id = manufacturerSet.getObject(1, Long.class);
        String name = manufacturerSet.getString("name");
        String country = manufacturerSet.getString("country");
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(id);
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        return manufacturer;
    }
}
