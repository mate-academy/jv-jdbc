package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.exeption.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertFormatRequest = "INSERT INTO manufacturers(name,country) VALUE (?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createdPreparedStatement = connection
                        .prepareStatement(insertFormatRequest, Statement.RETURN_GENERATED_KEYS)) {
            createdPreparedStatement.setString(1,manufacturer.getName());
            createdPreparedStatement.setString(2,manufacturer.getCountry());
            createdPreparedStatement.executeUpdate();
            ResultSet resultSet = createdPreparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Long id = resultSet.getObject(1,Long.class);
                manufacturer.setId(id);
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create manufacturer "
                    + manufacturer + " in DB", e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String insertFormatRequest = "SELECT * FROM manufacturers WHERE id = ?"
                + " AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturedPreparedStatement = connection
                        .prepareStatement(insertFormatRequest)) {
            getManufacturedPreparedStatement.setLong(1,id);
            ResultSet resultSet = getManufacturedPreparedStatement.executeQuery();
            Manufacturer manufacturer = null;
            if (resultSet.next()) {
                manufacturer = getManufacturer(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by " + id + " from DB", e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String insertFormatRequest = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        List<Manufacturer> allManufacturer = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturerPreparedStatement = connection
                        .prepareStatement(insertFormatRequest)) {
            ResultSet resultSet = getAllManufacturerPreparedStatement.executeQuery();
            while (resultSet.next()) {
                allManufacturer.add(getManufacturer(resultSet));
            }
            return allManufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturer from DB", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String insertFormatRequest =
                "UPDATE manufacturers SET name = ?, country = ? WHERE is_deleted = false "
                        + "AND id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement updatePreparedStatement = connection
                        .prepareStatement(insertFormatRequest)) {
            updatePreparedStatement.setLong(3,manufacturer.getId());
            updatePreparedStatement.setString(1,manufacturer.getName());
            updatePreparedStatement.setString(2,manufacturer.getCountry());
            updatePreparedStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer "
                    + manufacturer + " in DB", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String insertFormatRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deletePreparedStatement = connection
                        .prepareStatement(insertFormatRequest)) {
            deletePreparedStatement.setLong(1,id);
            return deletePreparedStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete colum by id " + id + " in DB", e);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) {
        Manufacturer manufacturer = new Manufacturer();
        try {
            manufacturer.setId(resultSet.getObject("id",Long.class));
            manufacturer.setName(resultSet.getString("name"));
            manufacturer.setCountry(resultSet.getString("country"));
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer from resultSet "
                    + resultSet, e);
        }
    }
}
