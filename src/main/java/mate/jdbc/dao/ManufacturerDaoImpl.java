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
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertQuery = "INSERT INTO manufacturers(name, country) VALUES(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement createStatement = connection
                         .prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            createStatement.setString(1, manufacturer.getName());
            createStatement.setString(2, manufacturer.getCountry());
            createStatement.executeUpdate();
            ResultSet generetedKeys = createStatement.getGeneratedKeys();
            if (generetedKeys.next()) {
                Long id = generetedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer in data base"
                    + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Manufacturer manufacturer = null;
        String getQuery = "SELECT * FROM manufacturers"
                + " WHERE id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getStatement = connection
                        .prepareStatement(getQuery)) {
            getStatement.setLong(1, id);
            ResultSet resultSet = getStatement.executeQuery();
            if (resultSet.next()) {
                manufacturer = getManufacturer(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by id " + id, e);
        }
        return Optional.ofNullable(manufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String getAllQuery = "SELECT * FROM manufacturers WHERE is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllStatement = connection
                        .createStatement()) {
            ResultSet resultSet = getAllStatement.executeQuery(getAllQuery);
            while (resultSet.next()) {
                manufacturers.add(getManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufactures from data base", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateQuerry = "UPDATE manufacturers SET name = ?, country = ?"
                + "where id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement = connection
                        .prepareStatement(updateQuerry)) {
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.setLong(3, manufacturer.getId());
            updateStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer " + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteQuerry = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteStatement = connection
                        .prepareStatement(deleteQuerry)) {
            deleteStatement.setLong(1, id);
            return deleteStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer by id " + id, e);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject(1, Long.class);
        String name = resultSet.getString(2);
        String country = resultSet.getString(3);
        return new Manufacturer(id, name, country);
    }
}
