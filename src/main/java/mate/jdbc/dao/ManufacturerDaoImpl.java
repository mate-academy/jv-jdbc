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
    private static final int INDEX_1 = 1;
    private static final int INDEX_2 = 2;
    private static final int INDEX_3 = 3;
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String COUNTRY = "country";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String createManufacturerRequest =
                "INSERT INTO manufacturers (name, country) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement = connection.prepareStatement(
                        createManufacturerRequest, Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(INDEX_1, manufacturer.getName());
            createManufacturerStatement.setString(INDEX_2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(INDEX_1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't create manufacturer with id " + manufacturer.getId(), e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        List<Manufacturer> allManufacturers = getAll();
        return allManufacturers.stream()
                .filter(m -> m.getId().equals(id))
                .findAny();
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllRequest =
                "SELECT * FROM manufacturers WHERE is_deleted = FALSE;";
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturersStatement =
                        connection.prepareStatement(getAllRequest)) {
            ResultSet resultSet = getAllManufacturersStatement.executeQuery(getAllRequest);
            while (resultSet.next()) {
                allManufacturers.add(setManufacturer(resultSet));
            }
            return allManufacturers;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET name = ?, "
                + "country = ? WHERE (id = ?) AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement =
                        connection.prepareStatement(updateRequest)) {
            createManufacturerStatement.setString(INDEX_1, manufacturer.getName());
            createManufacturerStatement.setString(INDEX_2, manufacturer.getCountry());
            createManufacturerStatement.setLong(INDEX_3, manufacturer.getId());
            if (createManufacturerStatement.executeUpdate() > 0) {
                return manufacturer;
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't update manufacturer with id " + manufacturer.getId(), e);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest =
                "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement =
                        connection.prepareStatement(deleteRequest)) {
            createManufacturerStatement.setLong(INDEX_1, id);
            return createManufacturerStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't delete manufacturer by id " + id,e);
        }
    }

    public Manufacturer setManufacturer(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        Long id = resultSet.getObject(ID, Long.class);
        String name = resultSet.getString(NAME);
        String country = resultSet.getString(COUNTRY);
        manufacturer.setId(id);
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        return manufacturer;
    }
}
