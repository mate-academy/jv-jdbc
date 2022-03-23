package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturer = "INSERT INTO manufacturer (name,country) values(?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement
                        = connection.prepareStatement(insertManufacturer,
                        Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1,manufacturer.getName());
            createManufacturerStatement.setString(2,manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1,Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't create new manufacturer",e);
        }
        return manufacturer;
    }

    @Override
        public Optional<Manufacturer> get(Long id) {
        String insertManufacturer = "SELECT * FROM manufacturer WHERE id = ? AND is_deleted = 0";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement
                        = connection.prepareStatement(insertManufacturer)) {
            getManufacturerStatement.setLong(1,id);
            ResultSet result = getManufacturerStatement.executeQuery();
            if (result.next()) {
                Long resultId = result.getLong("id");
                String name = result.getString("name");
                String country = result.getString("country");
                return Optional.of(new Manufacturer(resultId, name, country));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't create new manufacturer",e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> result = new ArrayList<>();
        String insertManufacturer = "SELECT * FROM manufacturer WHERE is_deleted = 0";
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllManufacturerStatement
                        = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturerStatement.executeQuery(insertManufacturer);
            while (resultSet.next()) {
                Long resultId = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                result.add(new Manufacturer(resultId, name, country));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't create new manufacturer",e);
        }
        return result;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String insertManufacturer = "UPDATE manufacturer SET name = ?,country = ? WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement
                         = connection.prepareStatement(insertManufacturer,
                        Statement.RETURN_GENERATED_KEYS)) {
            updateManufacturerStatement.setString(1,manufacturer.getName());
            updateManufacturerStatement.setString(2,manufacturer.getCountry());
            updateManufacturerStatement.setLong(3,manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = updateManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return manufacturer;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't create new manufacturer",e);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        String insertManufacturer = "UPDATE manufacturer SET is_deleted = 1 WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement
                        = connection.prepareStatement(insertManufacturer,
                        Statement.RETURN_GENERATED_KEYS)) {
            updateManufacturerStatement.setLong(1,id);
            updateManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = updateManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't create new manufacturer",e);
        }
        return false;
    }
}
