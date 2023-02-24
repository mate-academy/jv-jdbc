package mate.jdbc.lib;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import mate.jdbc.DataProcessingException;
import mate.jdbc.model.Manufacturer;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("can't create a jdbc driver ", e);
        }
    }

    public Manufacturer create(Manufacturer manufacturer) {
        String insertFormatRequest = "INSERT INTO Manufacturers(name, country)"
                + " VALUES(?,?)";
        int nameIndex = 1;
        int countryIndex = 2;
        int idIndex = 1;
        try (Connection connection = getConnection(); PreparedStatement creationStatement =
                connection.prepareStatement(insertFormatRequest, Statement.RETURN_GENERATED_KEYS)) {
            creationStatement.setString(nameIndex, manufacturer.getName());
            creationStatement.setString(countryIndex, manufacturer.getCountry());
            creationStatement.executeUpdate();
            ResultSet generatedKeys = creationStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(idIndex, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("can't insert manufacturer to DB ", e);
        }
        return manufacturer;
    }

    public Optional<Manufacturer> get(Long id) {
        String selectRequest = "SELECT * FROM Manufacturers WHERE id = ? AND is_deleted = FALSE";
        int idPosition = 1;
        int namePosition = 2;
        int countryPosition = 3;
        Manufacturer manufacturer = new Manufacturer();
        try (Connection connection = getConnection(); PreparedStatement selectStatement =
                connection.prepareStatement(selectRequest)) {
            selectStatement.setLong(idPosition, id);
            selectStatement.executeQuery();
            ResultSet generatedKeys = selectStatement.executeQuery();
            manufacturer.setId(id);
            if (generatedKeys.next()) {
                String name = generatedKeys.getObject(namePosition, String.class);
                String country = generatedKeys.getObject(countryPosition, String.class);
                manufacturer.setName(name);
                manufacturer.setCountry(country);
            }
            return Optional.of(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("can't insert manufacturer to DB ", e);
        }
    }

    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String selectRequest = "SELECT * FROM Manufacturers WHERE is_deleted = FALSE";
        try (Connection connection = getConnection(); Statement getAllStatement =
                connection.createStatement()) {
            ResultSet resultSet = getAllStatement.executeQuery(selectRequest);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                Long id = resultSet.getObject("id", Long.class);
                String country = resultSet.getString("country");
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setCountry(country);
                manufacturer.setId(id);
                manufacturer.setName(name);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("can't get all manufacturers from DB ", e);
        }
        return allManufacturers;
    }

    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE Manufacturers SET name = ?, country = ?"
                + " WHERE id = ? AND is_deleted = FALSE";
        int idPosition = 3;
        int namePosition = 1;
        int countryPosition = 2;
        try (Connection connection = getConnection(); PreparedStatement creationStatement =
                connection.prepareStatement(updateRequest, Statement.RETURN_GENERATED_KEYS)) {
            creationStatement.setLong(idPosition, manufacturer.getId());
            creationStatement.setString(namePosition, manufacturer.getName());
            creationStatement.setString(countryPosition, manufacturer.getCountry());
            creationStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("can't insert manufacturer to DB ", e);
        }
        return manufacturer;
    }

    public boolean delete(Long id) {
        String deleteRequest = "UPDATE Manufacturers SET is_deleted = true WHERE id = ?";
        int requestInitPosition = 1;
        int minimalOperationAmount = 1;
        try (Connection connection = getConnection(); PreparedStatement creationStatement =
                connection.prepareStatement(deleteRequest, Statement.RETURN_GENERATED_KEYS)) {
            creationStatement.setLong(requestInitPosition, id);
            return creationStatement.executeUpdate() >= minimalOperationAmount;
        } catch (SQLException e) {
            throw new DataProcessingException("can't insert manufacturer to DB ", e);
        }
    }

    private static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "12345678");
            return DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/taxi_service", dbProperties);
        } catch (SQLException e) {
            throw new DataProcessingException("can't create connection to DB ", e);
        }
    }
}
