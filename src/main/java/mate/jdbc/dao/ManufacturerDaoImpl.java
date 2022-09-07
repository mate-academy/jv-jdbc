package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.exceptions.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final int ID_COLUMN = 1;
    private static final int NAME_COLUMN = 2;
    private static final int COUNTRY_COLUMN = 3;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String sqlRequest = "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlRequest)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                manufacturer.setId(generatedKeys.getObject(ID_COLUMN, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert "
                    + manufacturer + " data to the database.", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String sqlRequest = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlRequest)) {
            ResultSet resultSet = statement.executeQuery();
            return Optional.of(setManufacturer(resultSet));
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't get data from the row with id " + id + " in the database.", e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String sqlRequest = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        List<Manufacturer> manufacturerList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlRequest)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                manufacturerList.add(setManufacturer(resultSet));
            }
            return manufacturerList;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get data from the database.", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String sqlRequest = "UPDATE manufacturers SET name = ?, country = ? "
                + "WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sqlRequest)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.setLong(3, manufacturer.getId());
            statement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update data of the database.", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String sqlRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sqlRequest)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't delete data in the database with the appropriate id " + id, e);
        }
    }

    private Manufacturer setManufacturer(ResultSet resultSet) {
        Manufacturer manufacturer = new Manufacturer();
        try {
            manufacturer.setId(resultSet.getObject(ID_COLUMN, Long.class));
            manufacturer.setName(resultSet.getString(NAME_COLUMN));
            manufacturer.setCountry(resultSet.getString(COUNTRY_COLUMN));
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get data from the database read.", e);
        }
    }
}
