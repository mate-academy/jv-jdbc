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
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String query = "INSERT INTO manufacturers (name, country) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufactureStatement =
                        connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            createManufactureStatement.setString(1, manufacturer.getName());
            createManufactureStatement.setString(2, manufacturer.getCountry());
            createManufactureStatement.executeUpdate();
            ResultSet resultSet = createManufactureStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Long id = resultSet.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert into DB " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String query = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getOneStatement = connection.prepareStatement(query)) {
            getOneStatement.setLong(1, id);
            ResultSet resultSet = getOneStatement.executeQuery();
            if (resultSet.next()) {
                Manufacturer manufacturer = createManufacturer(resultSet);
                return Optional.of(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> listManufacturer = new ArrayList<>();
        String query = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = getAllStatement.executeQuery();
            while (resultSet.next()) {
                Manufacturer manufacturer = createManufacturer(resultSet);
                listManufacturer.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
        return listManufacturer;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String query = "UPDATE manufacturers SET name = ?, country = ? "
                + "WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturer = connection.prepareStatement(query)) {
            updateManufacturer.setString(1, manufacturer.getName());
            updateManufacturer.setString(2, manufacturer.getCountry());
            updateManufacturer.setLong(3, manufacturer.getId());
            updateManufacturer.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer from DB"
                    + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturer = connection.prepareStatement(query)) {
            updateManufacturer.setLong(1, id);
            return updateManufacturer.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer from DB with id: "
                    + id, e);
        }
    }

    private Manufacturer createManufacturer(ResultSet resultSet) throws SQLException {
        Long idFromDB = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(idFromDB);
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        return manufacturer;
    }
}
