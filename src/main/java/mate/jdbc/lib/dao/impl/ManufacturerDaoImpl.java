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
    private static final String sqlRequestForGetDataFromDb
            = "SELECT * FROM manufacturers WHERE is_deleted = false";
    private static final String sqlRequestForAddManufacturerToDb
            = "INSERT INTO manufacturers(NAME, COUNTRY) VALUES(?, ?)";
    private static final String sqlRequestForUpdateDataById
            = "UPDATE manufacturers SET name = (?), country = (?) WHERE id = (?)";
    private static final String sqlRequestForDeleteById
            = "UPDATE manufacturers SET is_deleted = true WHERE id = (?)";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try (Connection connectToDB = ConnectionUtil.getConnection();
                    PreparedStatement statement = connectToDB
                            .prepareStatement(sqlRequestForAddManufacturerToDb,
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
        try (Connection connectToDB = ConnectionUtil.getConnection();
                 PreparedStatement getByIdStatement = connectToDB
                         .prepareStatement(sqlRequestForGetDataFromDb + " AND id=(?)")) {
            getByIdStatement.setLong(1, id);
            ResultSet resultSet = getByIdStatement.executeQuery();
            if (resultSet.next()) {
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(resultSet.getObject(1, Long.class));
                manufacturer.setName(resultSet.getString(2));
                manufacturer.setCountry(resultSet.getString(3));
                return Optional.of(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't read info from DB");
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allDataInDb = new ArrayList<>();
        try (Connection connectToDB = ConnectionUtil.getConnection();
                Statement getAllStatement = connectToDB.createStatement()) {
            ResultSet resultSet = getAllStatement.executeQuery(sqlRequestForGetDataFromDb);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Long id = resultSet.getObject("id", Long.class);
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(id);
                manufacturer.setName(name);
                manufacturer.setCountry(country);
                allDataInDb.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't read info from DB");
        }
        return allDataInDb;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        try (Connection connectToDB = ConnectionUtil.getConnection();
                 PreparedStatement updateStatement
                         = connectToDB.prepareStatement(sqlRequestForUpdateDataById)) {
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.setLong(3, manufacturer.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer in DB", e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connectToDB = ConnectionUtil.getConnection();
                 PreparedStatement deleteStatement = connectToDB
                         .prepareStatement(sqlRequestForDeleteById)) {
            deleteStatement.setLong(1, id);
            return deleteStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer from DB", e);
        }
    }
}
