package mate.jdbc.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.lib.Dao;
import mate.jdbc.manufacturer.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturersDaoImpl implements ManufacturersDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insert = "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (PreparedStatement insertStatement = ConnectionUtil.getConnection()
                .prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setString(1, manufacturer.getName());
            insertStatement.setString(2, manufacturer.getCountry());
            insertStatement.executeUpdate();
            ResultSet generatedKeys = insertStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not insert manufacturer to DB", e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String select = "SELECT id, name, country FROM manufacturers "
                + "WHERE id = ? AND is_deleted = false;";
        try (PreparedStatement getStatement = ConnectionUtil.getConnection()
                .prepareStatement(select)) {
            getStatement.setLong(1, id);
            ResultSet resultSet = getStatement.executeQuery();
            return resultSet.next() ? Optional.of(createManufacturer(resultSet)) : Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can not find manufacturer with id:" + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String selectAll = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Statement getAllStatement = ConnectionUtil.getConnection()
                .createStatement()) {
            ResultSet resultSet = getAllStatement.executeQuery(selectAll);
            while (resultSet.next()) {
                manufacturers.add(createManufacturer(resultSet));
            }
            return manufacturers;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get elements from table", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateElement = "UPDATE manufacturers SET name = ? , country = ? "
                + "WHERE id = ? AND is_deleted = false;";
        try (PreparedStatement updateStatement = ConnectionUtil.getConnection()
                .prepareStatement(updateElement)) {
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.setLong(3, manufacturer.getId());
            updateStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not update manufacturer " + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteElement = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (PreparedStatement deleteStatement = ConnectionUtil.getConnection()
                .prepareStatement(deleteElement)) {
            deleteStatement.setLong(1, id);
            int executeUpdate = deleteStatement.executeUpdate();
            return executeUpdate >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not delete manufacturer by id:" + id, e);
        }
    }

    private Manufacturer createManufacturer(ResultSet resultSet) {
        try {
            Manufacturer manufacturer = new Manufacturer();
            String name = resultSet.getString("name");
            String country = resultSet.getString("country");
            Long idFromSet = resultSet.getObject(1, Long.class);
            manufacturer.setId(idFromSet);
            manufacturer.setName(name);
            manufacturer.setCountry(country);
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get element from manufacturers", e);
        }
    }
}
