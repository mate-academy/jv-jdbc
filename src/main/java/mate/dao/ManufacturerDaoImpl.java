package mate.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.lib.Dao;
import mate.model.Manufacturer;
import mate.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest = "INSERT INTO manufacture(name, country) values(?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufactureStatement =
                        connection.prepareStatement(insertManufacturerRequest,
                                Statement.RETURN_GENERATED_KEYS);) {

            createManufactureStatement.setString(1, manufacturer.getName());
            createManufactureStatement.setString(2, manufacturer.getCountry());
            createManufactureStatement.executeUpdate();
            ResultSet generatedKeys = createManufactureStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Can`t insert manufacture to db", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String sqlSelect = "SELECT * FROM manufacture WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement =
                        connection.prepareStatement(sqlSelect);) {

            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement
                    .executeQuery();
            Manufacturer manufacturer = new Manufacturer();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Long resultId = resultSet.getObject("id", Long.class);
                manufacturer.setName(name);
                manufacturer.setCountry(country);
                manufacturer.setId(resultId);
                return Optional.of(manufacturer);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Can`t get all manufactures from DB", e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufactures = new ArrayList<>();
        String sqlSelect = "SELECT * FROM manufacture WHERE is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getManufacturerStatement = connection.createStatement();) {
            ResultSet resultSet = getManufacturerStatement
                    .executeQuery(sqlSelect);
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
            throw new RuntimeException("Can`t get all manufactures from DB", e);
        }
        return allManufactures;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String insertManufacturerRequest =
                "UPDATE manufacture SET name = ?, country = ? WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                   PreparedStatement createManufacturerStatement = (PreparedStatement)
                        connection.prepareStatement(insertManufacturerRequest);) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.setLong(3, manufacturer.getId());
            createManufacturerStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can`t insert manufacture to db", e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacture SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement =
                        (PreparedStatement) connection.prepareStatement(deleteRequest,
                                Statement.RETURN_GENERATED_KEYS);) {
            createManufacturerStatement.setLong(1, id);
            return createManufacturerStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new RuntimeException("Can`t insert manufacturer to db", e);
        }
    }
}
