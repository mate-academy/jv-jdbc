package mate.jdbc.lib.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.lib.Dao;
import mate.jdbc.lib.dao.ManufacturerDao;
import mate.jdbc.lib.dao.exceptions.DataProcessingException;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String sqlQueryForAddManufacturerToDb
                = "INSERT INTO manufacturers(name, country) VALUES(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection
                            .prepareStatement(sqlQueryForAddManufacturerToDb,
                                    Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                Long id = resultSet.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer to DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String sqlQueryForGetDataFromDb
                = "SELECT * FROM manufacturers WHERE is_deleted = false AND id=(?)";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection
                         .prepareStatement(sqlQueryForGetDataFromDb)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Manufacturer manufacturer = initializeManufacturer(resultSet);
                return Optional.of(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't read info from DB by id=" + id);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        String sqlQueryForGetDataFromDb
                = "SELECT * FROM manufacturers WHERE is_deleted = false";
        List<Manufacturer> allDataInDb = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlQueryForGetDataFromDb);
            while (resultSet.next()) {
                Manufacturer manufacturer = initializeManufacturer(resultSet);
                allDataInDb.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't read info from DB");
        }
        return allDataInDb;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String sqlQueryForUpdateDataById
                = "UPDATE manufacturers SET name = (?), country = (?) "
                + "WHERE id = (?) is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement
                         = connection.prepareStatement(sqlQueryForUpdateDataById)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.setLong(3, manufacturer.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer by " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String sqlQueryForDeleteById
                = "UPDATE manufacturers SET is_deleted = true WHERE id = (?)";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection
                         .prepareStatement(sqlQueryForDeleteById)) {
            statement.setLong(1, id);
            return statement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer from DB by id=" + id, e);
        }
    }

    private Manufacturer initializeManufacturer(ResultSet resultSet) {
        Manufacturer manufacturer = new Manufacturer();
        try {
            manufacturer.setId(resultSet.getObject(1, Long.class));
            manufacturer.setName(resultSet.getString(2));
            manufacturer.setCountry(resultSet.getString(3));
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not create new manufacturer by resultSet", e);
        }
    }
}
