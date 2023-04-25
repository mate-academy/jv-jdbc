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
import mate.jdbc.util.ConnectionUtil;
import mate.jdbc.util.DataProcessingException;

@Dao
public class ManufacturerDaoImp implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturer = "INSERT INTO manufacturers(name, country) VALUES(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturers = connection
                        .prepareStatement(insertManufacturer, Statement.RETURN_GENERATED_KEYS)) {
            createManufacturers.setString(1, manufacturer.getName());
            createManufacturers.setString(2, manufacturer.getCountry());
            createManufacturers.executeUpdate();
            ResultSet generatedKeys = createManufacturers.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer: " + manufacturer
                    + " to DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getStatement = "SELECT * FROM manufacturers WHERE id = ? "
                + "AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturer = connection
                        .prepareStatement(getStatement)) {
            getManufacturer.setLong(1, id);
            ResultSet resultSet = getManufacturer.executeQuery();
            resultSet.next();
            String name = resultSet.getString("name");
            String country = resultSet.getString("country");
            Manufacturer manufacturer = new Manufacturer(id, name, country);
            return Optional.of(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer with id: "
                    + id + " from DB.", e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String getAllStatement = "SELECT * FROM manufacturers WHERE is_deleted = FALSE";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturers = connection
                        .prepareStatement(getAllStatement)) {
            ResultSet resultSet = getAllManufacturers
                    .executeQuery();
            while (resultSet.next()) {
                Manufacturer manufacturer = generateManufacturer(resultSet);
                allManufacturers.add(manufacturer);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String insertManufacturer = "UPDATE manufacturers SET name = ?, country = ? WHERE id = ? "
                + "AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturers = connection
                        .prepareStatement(insertManufacturer)) {
            updateManufacturers.setString(1, manufacturer.getName());
            updateManufacturers.setString(2, manufacturer.getCountry());
            updateManufacturers.setLong(3, manufacturer.getId());
            updateManufacturers.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer: " + manufacturer
                    + " in DB", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String insertManufacturer = " UPDATE manufacturers SET is_deleted = true where id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturers = connection
                        .prepareStatement(insertManufacturer, Statement.RETURN_GENERATED_KEYS)) {
            deleteManufacturers.setLong(1, id);
            return deleteManufacturers.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer with id: " + id
                    + " from DB", e);
        }
    }

    private Manufacturer generateManufacturer(ResultSet resultSet) {
        try {
            Long id = resultSet.getObject("id", Long.class);
            String name = resultSet.getString("name");
            String country = resultSet.getString("country");
            return new Manufacturer(id, name, country);
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't create manufacturer " + resultSet, ex);
        }
    }
}

