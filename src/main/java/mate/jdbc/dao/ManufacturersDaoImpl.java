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
import mate.jdbc.manufacturer.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturersDaoImpl implements ManufacturersDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insert = "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement insertStatement
                        = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
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
                + "where id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getStatement = connection.prepareStatement(select)) {
            getStatement.setLong(1, id);
            ResultSet resultSet = getStatement.executeQuery();
            return resultSet.next() ? Optional.of(createManufacturer(resultSet)) : Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can not find id:" + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String selectAll = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllStatement = connection.createStatement()) {
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
                + "where id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement = connection.prepareStatement(updateElement)) {
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.setLong(3, manufacturer.getId());
            updateStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not update table", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteElement = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteStatement = connection.prepareStatement(deleteElement)) {
            deleteStatement.setLong(1, id);
            int executeUpdate = deleteStatement.executeUpdate();
            return executeUpdate >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not delete by id", e);
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
            throw new DataProcessingException("Can not read from DB", e);
        }
    }
}
