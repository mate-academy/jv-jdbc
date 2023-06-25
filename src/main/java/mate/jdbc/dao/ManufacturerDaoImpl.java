package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest = "INSERT INTO manufacturers(name,country) values(?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement =
                        connection.prepareStatement(insertManufacturerRequest,
                             Statement.RETURN_GENERATED_KEYS);) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                manufacturer.setId(generatedKeys.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't create manufacturer to Db", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getRequest = "SELECT * FROM manufacturers WHERE is_deleted = false AND id = ?";
        Manufacturer manufacturer = new Manufacturer();
        Optional<Manufacturer> result;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getStatement = connection.prepareStatement(getRequest);) {
            getStatement.setLong(1, id);
            ResultSet resultSet = getStatement.executeQuery();
            while (resultSet.next()) {
            manufacturer = getManufacturerFromResultSet(resultSet);
            }
            return result = Optional.of(manufacturer);
        } catch (SQLException e) {
            throw new RuntimeException("Can't get manufacturer from Db by id " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String getAllRequest = "SELECT * FROM manufacturers WHERE is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllStatement = connection.prepareStatement(getAllRequest);) {
            ResultSet resultSet = getAllStatement.executeQuery();
            while (resultSet.next()) {
                Manufacturer manufacturer = getManufacturerFromResultSet(resultSet);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get all manufacturers from Db", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET name = ?, "
                + "country = ? WHERE id = ? and is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement = connection.prepareStatement(updateRequest);) {
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.setLong(3, manufacturer.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can't update manufacturer to Db", e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deletedRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ? ";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deletedStatement = connection.prepareStatement(deletedRequest);) {
            deletedStatement.setLong(1, id);
            return deletedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete manufacturer from Db by id " + id, e);
        }
    }

    private Manufacturer getManufacturerFromResultSet(ResultSet resultSet) {
        Manufacturer manufacturer = new Manufacturer();
        try {
            manufacturer.setId(resultSet.getObject("id", Long.class));
            manufacturer.setName(resultSet.getString("name"));
            manufacturer.setCountry(resultSet.getString("country"));
            return manufacturer;
        } catch (SQLException e) {
            throw new RuntimeException("Can't get resultSet ",e);
        }
    }
}
