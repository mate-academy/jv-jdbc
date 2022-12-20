package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.exceptions.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest = "INSERT INTO manufacturers(name,country) values(?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement createManufacturesStatement =
                         connection.prepareStatement(insertManufacturerRequest,
                             Statement.RETURN_GENERATED_KEYS)) {
            createManufacturesStatement.setString(1,manufacturer.getName());
            createManufacturesStatement.setString(2,manufacturer.getCountry());
            createManufacturesStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturesStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cant insert Manufacturer" + manufacturer,e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getRequest = "SELECT * FROM manufacturers where id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getStatement = connection.prepareStatement(getRequest)) {
            getStatement.setLong(1,id);
            ResultSet resultSet = getStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(createManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cant get manufacturer from DB y index " + id,e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufactures = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                 Statement getAllManufactures = connection.createStatement()) {
            ResultSet resultSet = getAllManufactures
                        .executeQuery("SELECT * FROM manufacturers where is_deleted = false");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Long id = resultSet.getObject("id", Long.class);
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setName(name);
                manufacturer.setCountry(country);
                manufacturer.setId(id);
                allManufactures.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cant get all manufactures",e);
        }
        return allManufactures;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String deleteRequest =
                "UPDATE manufacturers SET name = ?,country = ? where id =? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement createManufacturesStatement =
                          connection.prepareStatement(deleteRequest,
                                  Statement.RETURN_GENERATED_KEYS)) {
            createManufacturesStatement.setString(1,manufacturer.getName());
            createManufacturesStatement.setString(2,manufacturer.getCountry());
            createManufacturesStatement.setLong(3,manufacturer.getId());
            createManufacturesStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Cant update Manufacturer " + manufacturer,e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true where id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement createManufacturesStatement =
                        connection.prepareStatement(deleteRequest,
                                Statement.RETURN_GENERATED_KEYS)) {
            createManufacturesStatement.setLong(1,id);
            return createManufacturesStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Cant insert Manufacturer",e);
        }
    }

    private Manufacturer createManufacturer(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        Long id = resultSet.getLong("id");
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        manufacturer.setId(id);
        return manufacturer;
    }
}
