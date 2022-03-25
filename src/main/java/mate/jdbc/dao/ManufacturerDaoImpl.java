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
            throw new RuntimeException("Can't create new manufacturer" + manufacturer,e);
        }
        return manufacturer;
    }

    @Override
        public Optional<Manufacturer> get(Long id) {
        String getManufacturer = "SELECT * FROM manufacturer WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement
                        = connection.prepareStatement(getManufacturer)) {
            getManufacturerStatement.setLong(1,id);
            ResultSet result = getManufacturerStatement.executeQuery();
            if (result.next()) {
                return Optional.of(convertToManufacturer(result));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get manufacturer by id = " + id,e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String getAllManufacturers = "SELECT * FROM manufacturer WHERE is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllManufacturerStatement
                        = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturerStatement.executeQuery(getAllManufacturers);
            while (resultSet.next()) {
                manufacturers.add(convertToManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get all manufacturers from db",e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturer = "UPDATE manufacturer SET name = ?,country = ? WHERE id = ? "
                + "AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement
                         = connection.prepareStatement(updateManufacturer,
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
            throw new RuntimeException("Can't update manufacturer by id = "
                + manufacturer.getId(),e);
        }
        throw new RuntimeException("Can't update manufacturer by id = "
            + manufacturer.getId());
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufacturer = "UPDATE manufacturer SET is_deleted = 1 WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement
                        = connection.prepareStatement(deleteManufacturer,
                        Statement.RETURN_GENERATED_KEYS)) {
            updateManufacturerStatement.setLong(1,id);
            return updateManufacturerStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete manufacturer by id = " + id,e);
        }
    }

    private Manufacturer convertToManufacturer(ResultSet enteringSet) {
        try {
            Long id = enteringSet.getLong("id");
            String name = enteringSet.getString("name");
            String country = enteringSet.getString("country");
            return new Manufacturer(id, name, country);
        } catch (SQLException e) {
            throw new RuntimeException("Can`t convert from ResultSet : " + enteringSet,e);
        }
    }
}
