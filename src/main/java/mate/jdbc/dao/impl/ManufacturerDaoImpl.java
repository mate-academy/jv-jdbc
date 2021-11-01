package mate.jdbc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) throws DataFormatException {
        String insertManufacturerRequest = "INSERT INTO manufacturers(name,country) values(?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement = connection
                        .prepareStatement(insertManufacturerRequest,
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
            throw new DataFormatException("Can't insert car to DB");
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) throws DataFormatException {
        String getManufacturerRequest = "SELECT * FROM manufacturers"
                + " where is_deleted = false and id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getManufacturerStatement = connection
                        .prepareStatement(getManufacturerRequest)) {
            getManufacturerStatement.setLong(1,id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            resultSet.next();
            String name = resultSet.getString(2);
            String country = resultSet.getString(3);
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setName(name);
            manufacturer.setCountry(country);
            manufacturer.setId(id);
            return Optional.of(manufacturer);
        } catch (SQLException e) {
            throw new DataFormatException("DB hasn't manufacturer with id " + id + ".");
        }
    }

    @Override
    public List<Manufacturer> getAll() throws DataFormatException {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                 Statement getAllManufacturersStatement = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturersStatement
                    .executeQuery("SELECT * FROM manufacturers where is_deleted = false");
            while (resultSet.next()) {
                Long id = resultSet.getObject("id",Long.class);
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(id);
                manufacturer.setName(name);
                manufacturer.setCountry(country);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataFormatException("Can't get all manufacturers from DB");
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) throws DataFormatException {
        String updateManufacturerRequest = "UPDATE manufacturers SET name = ?, "
                + "country = ? where id = ? and is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getManufacturerStatement = connection
                         .prepareStatement(updateManufacturerRequest)) {
            getManufacturerStatement.setString(1,manufacturer.getName());
            getManufacturerStatement.setString(2,manufacturer.getCountry());
            getManufacturerStatement.setLong(3,manufacturer.getId());
            getManufacturerStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataFormatException("Can't update manufacturer " + manufacturer.getName());
        }
    }

    @Override
    public boolean delete(Long id) throws DataFormatException {
        String deleteRequest = "UPDATE manufacturers "
                + "SET is_deleted = true where id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement = connection
                        .prepareStatement(deleteRequest)) {
            deleteManufacturerStatement.setLong(1,id);
            return deleteManufacturerStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataFormatException("Can't insert car to DB");
        }
    }
}
