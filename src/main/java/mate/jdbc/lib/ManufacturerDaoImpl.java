package mate.jdbc.lib;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.DataProcessingException;
import mate.jdbc.model.Manufacturer;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final int NAME_INDEX = 1;
    private static final int COUNTRY_INDEX = 2;
    private static final int ID_INDEX = 1;
    private static final int REQUEST_INIT_INDEX = 1;
    private static final int MINIMAL_OPERATION_AMOUNT = 1;

    public Manufacturer create(Manufacturer manufacturer) {
        String insertFormatRequest = "INSERT INTO Manufacturers(name, country)"
                + " VALUES(?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement creationStatement =
                    connection.prepareStatement(insertFormatRequest, Statement.RETURN_GENERATED_KEYS)) {
            creationStatement.setString(NAME_INDEX, manufacturer.getName());
            creationStatement.setString(COUNTRY_INDEX, manufacturer.getCountry());
            creationStatement.executeUpdate();
            ResultSet generatedKeys = creationStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(ID_INDEX, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("can't insert manufacturer to DB ", e);
        }
        return manufacturer;
    }

    public Optional<Manufacturer> get(Long id) {
        String selectRequest = "SELECT * FROM Manufacturers WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement selectStatement =
                    connection.prepareStatement(selectRequest)) {
            selectStatement.setLong(ID_INDEX, id);
            selectStatement.executeQuery();
            ResultSet generatedKeys = selectStatement.executeQuery();
            return Optional.of(parseResultSet(generatedKeys));
        } catch (SQLException e) {
            throw new DataProcessingException("can't get manufacturer from DB " + id, e);
        }
    }

    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String selectRequest = "SELECT * FROM Manufacturers WHERE is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection(); Statement getAllStatement =
                connection.createStatement()) {
            ResultSet resultSet = getAllStatement.executeQuery(selectRequest);
            while (resultSet.next()) {
                allManufacturers.add(parseResultSet(resultSet));
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
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement creationStatement =
                    connection.prepareStatement(updateRequest, Statement
                             .RETURN_GENERATED_KEYS)) {
            creationStatement.setLong(idPosition, manufacturer.getId());
            creationStatement.setString(NAME_INDEX, manufacturer.getName());
            creationStatement.setString(COUNTRY_INDEX, manufacturer.getCountry());
            creationStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("can't update manufacturer:"
                    + manufacturer + " in DB ", e);
        }
        return manufacturer;
    }

    public boolean delete(Long id) {
        String deleteRequest = "UPDATE Manufacturers SET is_deleted = TRUE WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement creationStatement =
                    connection.prepareStatement(deleteRequest, Statement.RETURN_GENERATED_KEYS)) {
            creationStatement.setLong(REQUEST_INIT_INDEX, id);
            return creationStatement.executeUpdate() >= MINIMAL_OPERATION_AMOUNT;
        } catch (SQLException e) {
            throw new DataProcessingException("can't delete manufacturer from DB with id: "
                    + id, e);
        }
    }

    private static Manufacturer parseResultSet(ResultSet set) {
        Manufacturer manufacturer = new Manufacturer();
        try {
            if (set.next()) {
                Long id = set.getLong("id");
                String name = set.getString("name");
                String country = set.getString("country");
                manufacturer.setId(id);
                manufacturer.setName(name);
                manufacturer.setCountry(country);
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer from set " + set, e);
        }
    }
}
