package mate.jdbc.dao;

import static mate.jdbc.util.ConnectionUtil.getConnection;

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
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String query = "INSERT INTO manufacturers(name, country) VALUES(?, ?);";
        try (Connection conn = getConnection();
                PreparedStatement updateManufacturerStatement =
                        conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = updateManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can`t create manufacturer: " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String query = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false;";
        try (Connection conn = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement = conn.prepareStatement(query)
        ) {
            getManufacturerStatement.setLong(1, id);
            ResultSet generatedKeys = getManufacturerStatement.executeQuery();
            if (generatedKeys.next()) {
                return Optional.of(getManufacturerFromGeneratedKeys(generatedKeys));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can`t get manufacturer by id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        String query = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        List<Manufacturer> listOfManufacturers = new ArrayList<>();
        try (Connection conn = getConnection();
                PreparedStatement getAllManufacturersStatement = conn.prepareStatement(query);
                ResultSet generatedKeys = getAllManufacturersStatement.executeQuery()
        ) {
            while (generatedKeys.next()) {
                listOfManufacturers.add(getManufacturerFromGeneratedKeys(generatedKeys));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can`t get manufacturers", e);
        }
        return listOfManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String query = "UPDATE manufacturers SET name = ?, country = ?"
                + " WHERE id = ? AND is_deleted = false;";
        try (Connection conn = getConnection();
                PreparedStatement updateManufacturerStatement = conn.prepareStatement(query)
        ) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can`t update manufacturer: " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection conn = getConnection();
                PreparedStatement deleteManufacturerStatement = conn.prepareStatement(query)
        ) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new RuntimeException("Can`t delete manufacturer with id: " + id, e);
        }
    }

    private Manufacturer getManufacturerFromGeneratedKeys(ResultSet generatedKeys)
            throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(generatedKeys.getObject(1, Long.class));
        manufacturer.setName(generatedKeys.getString(2));
        manufacturer.setCountry(generatedKeys.getString(3));
        return manufacturer;
    }
}
