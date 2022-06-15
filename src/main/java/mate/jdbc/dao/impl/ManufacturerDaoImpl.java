package mate.jdbc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String createQuery = "INSERT INTO manufacturers (name, country) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createStatement =
                        connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)) {
            createStatement.setString(1, manufacturer.getName());
            createStatement.setString(2, manufacturer.getCountry());
            createStatement.executeUpdate();
            ResultSet generatedKeys = createStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                manufacturer.setId(generatedKeys.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DateTimeException("Cant create manufacturer " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String selectByIdQuery = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getByIdStatement = connection.prepareStatement(selectByIdQuery)) {
            getByIdStatement.setLong(1, id);
            ResultSet resultSet = getByIdStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getFromResultSet(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DateTimeException("Cant get manufacturer by id " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String selectAllQuery = " SELECT * FROM manufacturers WHERE is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllStatement = connection.prepareStatement(selectAllQuery)) {
            ResultSet resultSet = getAllStatement.executeQuery();
            List<Manufacturer> manufacturers = new ArrayList<>();
            while (resultSet != null && resultSet.next()) {
                manufacturers.add(getFromResultSet(resultSet));
            }
            return manufacturers;
        } catch (SQLException e) {
            throw new DateTimeException("Cant getAll manufacturers ", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateQuery =
                "UPDATE manufacturers "
                        + "SET name = ?, country = ? WHERE (id = ?) "
                        + "AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updatePreparedStatement =
                        connection.prepareStatement(updateQuery)) {
            updatePreparedStatement.setString(1, manufacturer.getName());
            updatePreparedStatement.setString(2, manufacturer.getCountry());
            updatePreparedStatement.setLong(3, manufacturer.getId());
            updatePreparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DateTimeException("Cant update manufacturers  " + manufacturer.getName(), e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteQuery = "UPDATE manufacturers SET is_deleted = true WHERE (id = ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deletePreparedStatement =
                        connection.prepareStatement(deleteQuery)) {
            deletePreparedStatement.setLong(1, id);
            return deletePreparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DateTimeException("Cant delete manufacturers by id  " + id, e);
        }
    }

    private Manufacturer getFromResultSet(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        manufacturer.setId(id);
        return manufacturer;
    }
}
