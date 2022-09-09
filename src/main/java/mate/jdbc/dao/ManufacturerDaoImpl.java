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
        String insertManufacturer = "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement =
                        connection.prepareStatement(insertManufacturer,
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
            throw new DataProcessingException("Can't insert manufacturer to DB. ", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturerSql = "SELECT * FROM manufacturers "
                + "WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturer = connection.prepareStatement(
                        getManufacturerSql)) {
            getManufacturer.setObject(1, id);
            ResultSet resultSet = getManufacturer.executeQuery();
            if (resultSet.next()) {
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId((Long) resultSet.getObject(1));
                manufacturer.setName(resultSet.getString(2));
                manufacturer.setCountry(resultSet.getString(3));
                return Optional.of(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get a manufacturer from DB by id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        String getManufacturersSql = "SELECT * FROM manufacturers WHERE is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturers = connection.prepareStatement(
                         getManufacturersSql)) {
            ResultSet resultSet = getManufacturers.executeQuery();
            return extractManufacturers(resultSet);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB. ", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateSql = "UPDATE manufacturers SET name = ?, country = ?"
                + " WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(
                        updateSql)) {
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.setObject(3, manufacturer.getId());
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer with id = "
                    + manufacturer.getId()
                    + " in manufacturers table. ", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteSql = "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(
                        deleteSql)) {
            preparedStatement.setObject(1, id);
            return preparedStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer by id = "
                    + id + " from manufacturers table. ", e);
        }
    }

    private List<Manufacturer> extractManufacturers(ResultSet resultSet) throws SQLException {
        List<Manufacturer> manufacturersList = new ArrayList<>();
        while (resultSet.next()) {
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setId(resultSet.getObject(1, Long.class));
            manufacturer.setName(resultSet.getString(2));
            manufacturer.setCountry(resultSet.getString(3));
            manufacturersList.add(manufacturer);
        }
        return manufacturersList;
    }
}
