package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.exeption.DataProcessingException;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertFormatRequest = "INSERT INTO manufacturer(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createFormatStatement =
                        connection.prepareStatement(insertFormatRequest,
                             Statement.RETURN_GENERATED_KEYS)) {
            createFormatStatement.setString(1, manufacturer.getName());
            createFormatStatement.setString(2, manufacturer.getCountry());
            createFormatStatement.executeUpdate();
            ResultSet generatedKeys = createFormatStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create manufacturer " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String manufacturerByIdQuery = "SELECT * FROM manufacturer "
                + "WHERE is_deleted = FALSE AND id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerByIdStatement
                        = connection.prepareStatement(manufacturerByIdQuery)) {
            getManufacturerByIdStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerByIdStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getManufacturer(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer from DB by id: " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllManufacturersStatement = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturersStatement
                    .executeQuery("SELECT * FROM manufacturer where is_deleted = false;");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Long id = resultSet.getObject("id", Long.class);
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(id);
                manufacturer.setName(name);
                manufacturer.setCountry(country);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerQuery = "UPDATE manufacturer SET name = ?, country = ? "
                + "WHERE is_deleted = FALSE AND id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturersStatement
                        = connection.prepareStatement(updateManufacturerQuery)) {
            updateManufacturersStatement.setString(1, manufacturer.getName());
            updateManufacturersStatement.setString(2, manufacturer.getCountry());
            updateManufacturersStatement.setLong(3, manufacturer.getId());
            updateManufacturersStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer " + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String request = "UPDATE manufacturer SET is_deleted = true where id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createFormatStatement =
                        connection.prepareStatement(request)) {
            createFormatStatement.setLong(1, id);
            return createFormatStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete by id " + id, e);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) {
        Manufacturer manufacturer = new Manufacturer();
        try {
            manufacturer.setId(resultSet.getObject("id", Long.class));
            manufacturer.setName(resultSet.getObject("name", String.class));
            manufacturer.setCountry(resultSet.getObject("country", String.class));
        } catch (SQLException e) {
            throw new RuntimeException("Can't get manufacturer from " + resultSet, e);
        }
        return manufacturer;
    }
}
