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
        String insertManufacturerQuery = "INSERT INTO manufacturers(name,country) values(?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement insertManufacturerStatement = connection
                        .prepareStatement(insertManufacturerQuery,
                                Statement.RETURN_GENERATED_KEYS)) {
            insertManufacturerStatement.setString(1, manufacturer.getName());
            insertManufacturerStatement.setString(2, manufacturer.getCountry());
            insertManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = insertManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert to DB "
                    + "next manufacturer: " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturersQuery = "SELECT * FROM manufacturers WHERE id =" + id
                + " AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturer = connection
                        .prepareStatement(getManufacturersQuery)) {
            ResultSet resultSet = getManufacturer.executeQuery(getManufacturersQuery);
            if (resultSet.next()) {
                System.out.println("SELECT operation from DB done. Row returned 1");
                return Optional.of(retrieveFromDB(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer from DB, by id " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturerList = new ArrayList<>();
        String getAllManufacturersQuery = "SELECT * FROM manufacturers WHERE is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturersStatement
                        = connection.prepareStatement(getAllManufacturersQuery)) {
            ResultSet resultSet = getAllManufacturersStatement
                    .executeQuery(getAllManufacturersQuery);
            while (resultSet.next()) {
                manufacturerList.add(retrieveFromDB(resultSet));
            }
            System.out.printf("SELECT ALL operation from DB done. "
                    + "Row returned %d \n", manufacturerList.size());
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
        return manufacturerList;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerQuery = "UPDATE manufacturers SET name = ?, "
                + "country = ? WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement
                        = connection.prepareStatement(updateManufacturerQuery)) {
            updateManufacturerStatement.setString(1,manufacturer.getName());
            updateManufacturerStatement.setString(2,manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            int rowUpdated = updateManufacturerStatement.executeUpdate();
            System.out.printf("UPDATE operation done. Row updated %d \n", rowUpdated);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update from DB "
                    + "next manufacturer: " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufacturerQuery = "UPDATE manufacturers SET is_deleted = true where id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement
                        = connection.prepareStatement(deleteManufacturerQuery)) {
            deleteManufacturerStatement.setLong(1, id);
            int rowUpdated = deleteManufacturerStatement.executeUpdate();
            System.out.printf("DELETE operation done. Row deleted %d \n", rowUpdated);
            return rowUpdated > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer to DB by id " + id, e);
        }
    }

    private Manufacturer retrieveFromDB(ResultSet resultSet) {
        Manufacturer newManufacturer;
        try {
            Long manufacturerId = resultSet.getObject("id", Long.class);
            String manufacturerName = resultSet.getString("name");
            String manufacturerCountry = resultSet.getString("country");
            newManufacturer = createNewManufacturer(manufacturerId,
                    manufacturerName, manufacturerCountry);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't retrieve information from DB", e);
        }
        return newManufacturer;
    }

    private Manufacturer createNewManufacturer(Long manufacturerId, String manufacturerName,
                                       String manufacturerCountry) {
        return new Manufacturer(manufacturerId,manufacturerName,
                manufacturerCountry);
    }
}
