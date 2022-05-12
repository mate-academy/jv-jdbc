package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.custom.Exception;
import mate.jdbc.lib.Dao;
import mate.jdbc.manufacturer.Manufacturer;
import mate.jdbc.util.UtilClass;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try (Connection connection = UtilClass.getConnection();
                 PreparedStatement createdManufacturerStatement = connection
                         .prepareStatement("INSERT INTO manufacturers(name, country) values(?, ?);",
                         Statement.RETURN_GENERATED_KEYS)) {
            createdManufacturerStatement.setString(1, manufacturer.getName());
            createdManufacturerStatement.setString(2, manufacturer.getCountry());
            createdManufacturerStatement.executeUpdate();
            ResultSet generatedKey = createdManufacturerStatement.getGeneratedKeys();
            if (generatedKey.next()) {
                Long id = generatedKey.getObject(1, Long.class);
                manufacturer.setId(id);
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new Exception("Can't create manufactures in DB taxi_db", e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        try (Connection connection = UtilClass.getConnection();
                PreparedStatement getManufacturerByIdStatement = connection
                        .prepareStatement("SELECT * FROM manufacturers "
                         + "WHERE is_deleted = FALSE AND id = ?;")) {
            getManufacturerByIdStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerByIdStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(setManufacturer(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new Exception("Can't get manufacturers from DB by id: " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturer = new ArrayList<>();
        try (Connection connection = UtilClass.getConnection();
                 Statement getCarStatements = connection.createStatement()) {
            ResultSet resultSet = getCarStatements
                    .executeQuery("SELECT * FROM manufacturers WHERE is_deleted = FALSE");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Long id = resultSet.getObject("id", Long.class);
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(id);
                manufacturer.setName(name);
                manufacturer.setCountry(country);
                allManufacturer.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new Exception("can't getAll manufacturers", e);
        }
        return allManufacturer;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        try (Connection connection = UtilClass.getConnection();
                 PreparedStatement updateManufacturerStatement
                        = connection.prepareStatement("UPDATE manufacturers SET name = ?, "
                        + "country = ? WHERE is_deleted = FALSE AND id = ?;")) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new Exception("Can't update manufacturers ", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = UtilClass.getConnection();
                 PreparedStatement deleteStatement = connection
                         .prepareStatement("UPDATE manufacturers SET is_deleted = TRUE "
                             + "WHERE id = ?;")) {
            deleteStatement.setLong(1, id);
            return deleteStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new Exception("Can't delete some manufacturer from DB", e);
        }
    }

    private Manufacturer setManufacturer(ResultSet resultSet) {
        Manufacturer manufacturer = new Manufacturer();
        try {
            manufacturer.setId(resultSet.getObject(1, Long.class));
            manufacturer.setName(resultSet.getObject(2, String.class));
            manufacturer.setCountry(resultSet.getObject(3, String.class));
        } catch (SQLException e) {
            throw new Exception("Can't set manufacturer in arrayList", e);
        }
        return manufacturer;
    }
}
