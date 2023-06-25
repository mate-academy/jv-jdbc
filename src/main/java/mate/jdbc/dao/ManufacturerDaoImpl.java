package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

public class ManufacturerDaoImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufactureRequest = "INSERT INTO manufacturers(name, country) values(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufactureStatement =
                        connection.prepareStatement(insertManufactureRequest,
                        Statement.RETURN_GENERATED_KEYS)) {
            createManufactureStatement.setString(1, manufacturer.getName());
            createManufactureStatement.setString(2, manufacturer.getCountry());
            createManufactureStatement.executeUpdate();
            ResultSet generatedKeys = createManufactureStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                manufacturer.setId(generatedKeys.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cant create new data in database", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getAllManufactureRequest = "SELECT * FROM manufacturers WHERE isDeleted = 0";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturesStatement = connection
                        .prepareStatement(getAllManufactureRequest);) {
            ResultSet resultSet = getAllManufacturesStatement
                                    .executeQuery(getAllManufactureRequest);
            while (resultSet.next()) {
                if (Objects.equals(resultSet.getObject("id", Long.class), id)) {
                    Long objectId = resultSet.getObject("id", Long.class);
                    String name = resultSet.getString("name");
                    String country = resultSet.getString("country");
                    return Optional.of(new Manufacturer(objectId, name, country));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cant get data by id " + id + "from database", e);
        }
        return Optional.of(new Manufacturer());
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturersList = new ArrayList<>();
        String getAllFormatRequest = "SELECT * FROM manufacturers WHERE isDeleted = 0";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturesStatement = connection
                        .prepareStatement(getAllFormatRequest);) {
            ResultSet resultSet = getAllManufacturesStatement.executeQuery(getAllFormatRequest);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Long id = resultSet.getObject("id", Long.class);
                Manufacturer manufacturer = new Manufacturer(id, name, country);
                manufacturersList.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cant get all data from database", e);
        }
        return manufacturersList;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufactureRequest = "UPDATE manufacturers SET name = ?, "
                + "country = ? WHERE id = ? AND isDeleted = 0";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufactureStatement =
                        connection.prepareStatement(updateManufactureRequest,
                             Statement.RETURN_GENERATED_KEYS);) {
            createManufactureStatement.setString(1, manufacturer.getName());
            createManufactureStatement.setString(2, manufacturer.getCountry());
            createManufactureStatement.setLong(3, manufacturer.getId());
            createManufactureStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Cant update data from database", e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufactureRequest = "UPDATE manufacturers SET isDeleted = 1 "
                + "WHERE id = ? AND isDeleted = 0";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufactureStatement =
                        connection.prepareStatement(deleteManufactureRequest,
                             Statement.RETURN_GENERATED_KEYS);) {
            createManufactureStatement.setLong(1, id);
            createManufactureStatement.executeUpdate();
            return createManufactureStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Cant update data from database", e);
        }
    }
}
