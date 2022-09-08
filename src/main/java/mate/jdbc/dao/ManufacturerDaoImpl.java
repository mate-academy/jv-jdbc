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

public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufactureRequest = "INSERT INTO manufacturers(name, country) VALUES(?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufactureStatement =
                        connection.prepareStatement(insertManufactureRequest,
                                Statement.RETURN_GENERATED_KEYS)) {
            createManufactureStatement.setString(1, manufacturer.getName());
            createManufactureStatement.setString(2, manufacturer.getCountry());
            createManufactureStatement.executeUpdate();
            ResultSet generateKeys = createManufactureStatement.getGeneratedKeys();
            if (generateKeys.next()) {
                Long id = generateKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't create insert manufacture to DB");
        }

        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturersStatement = "SELECT * FROM manufacturers "
                + "WHERE is_deleted = false AND id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufactureStatement =
                        connection.prepareStatement(getManufacturersStatement,
                             Statement.RETURN_GENERATED_KEYS)) {
            getManufactureStatement.setLong(1, id);
            ResultSet foundResult = getManufactureStatement.executeQuery();
            Manufacturer manufacturer = null;
            if (foundResult.next()) {
                String name = foundResult.getString("name");
                String country = foundResult.getString("country");
                manufacturer = new Manufacturer(id, name, country);
            }

            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer from DB by id = " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllManufacturersStatement = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturersStatement
                    .executeQuery("SELECT * FROM manufacturers WHERE is_deleted = false");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                Long id = resultSet.getObject("id", Long.class);
                String country = resultSet.getString("country");
                Manufacturer manufacturer = new Manufacturer(id, name, country);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all cars from DB", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        Long id = manufacturer.getId();
        String name = manufacturer.getName();
        String country = manufacturer.getCountry();
        String updateStatement = "UPDATE manufacturers SET name = ?, country = ? "
                + "WHERE is_deleted = false and id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement =
                        connection.prepareStatement(updateStatement,
                                Statement.RETURN_GENERATED_KEYS)) {
            updateManufacturerStatement.setString(1, name);
            updateManufacturerStatement.setString(2, country);
            updateManufacturerStatement.setLong(3, id);
            updateManufacturerStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufcturer with id = " + id, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufactureStatement =
                        connection.prepareStatement(deleteRequest,
                                Statement.RETURN_GENERATED_KEYS)) {
            deleteManufactureStatement.setLong(1, id);
            return deleteManufactureStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find manufacturer with id = " + id, e);
        }
    }
}
