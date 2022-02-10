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
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest = "INSERT INTO manufacturers(name,country) values(?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement =
                        connection.prepareStatement(insertManufacturerRequest,
                             Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't insert format to DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturerRequest =
                "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement =
                        connection.prepareStatement(getManufacturerRequest)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get id manufacturer", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturer = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getManufacturerStatement = connection.createStatement()) {
            ResultSet resultSet = getManufacturerStatement
                    .executeQuery("SELECT * FROM manufacturers WHERE is_deleted = false");
            while (resultSet.next()) {
                allManufacturer.add(getManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get all manufacturer from DB", e);
        }
        return allManufacturer;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String insertUpdateRequest = "UPDATE manufacturers SET name = ?,"
                + " country = ? WHERE id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getUpdateStatement =
                        connection.prepareStatement(insertUpdateRequest)) {
            getUpdateStatement.setString(1, manufacturer.getName());
            getUpdateStatement.setString(2, manufacturer.getCountry());
            getUpdateStatement.setLong(3, manufacturer.getId());
            getUpdateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Cann't update manufacturer", e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String insertDeleteRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteStatement =
                        connection.prepareStatement(insertDeleteRequest)) {
            deleteStatement.setLong(1, id);
            return deleteStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer's id ", e);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(id);
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        return manufacturer;
    }
}
