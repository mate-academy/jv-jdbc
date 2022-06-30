package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mate.jdbc.exceptions.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertToDbRequest = "INSERT INTO manufacturers(name, country) "
                + "VALUES(?,?);";
        try {
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement createManufacturerStatement =
                    connection.prepareStatement(insertToDbRequest, Statement.RETURN_GENERATED_KEYS);
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long manufacturerId = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(manufacturerId);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create manufacturer " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Manufacturer get(Long id) {
        String getByIdRequest = "SELECT * "
                + "FROM manufacturers "
                + "WHERE id = ? "
                + "AND is_deleted = false;";
        Manufacturer manufacturer = new Manufacturer();
        try {
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement createManufacturerStatement
                    = connection.prepareStatement(getByIdRequest);
            createManufacturerStatement.setObject(1, id);
            ResultSet resultSet = createManufacturerStatement.executeQuery();
            while (resultSet.next()) {
                manufacturer = parseManufacturer(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufactured from DB by ID " + id, e);
        }
        return manufacturer;
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllFromDbRequest = "SELECT * "
                + "FROM manufacturers "
                + "WHERE is_deleted = false;";
        List<Manufacturer> manufacturerList = new ArrayList<>();
        try {
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement createManufacturerStatement
                    = connection.prepareStatement(getAllFromDbRequest);
            ResultSet resultSet = createManufacturerStatement.executeQuery();
            while (resultSet.next()) {
                manufacturerList.add(parseManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
        return manufacturerList;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers "
                + "SET name = ?, country = ?"
                + " WHERE id = ? AND is_deleted = false;";
        try {
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement createManufacturerStatement
                    = connection.prepareStatement(updateRequest);
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.setObject(3, manufacturer.getId());
            createManufacturerStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer from DB. Manufacturer = "
                    + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers "
                + "SET is_deleted = true "
                + "WHERE id = ?;";
        try {
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement createManufacturerStatement
                    = connection.prepareStatement(deleteRequest);
            createManufacturerStatement.setLong(1, id);
            return createManufacturerStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer from DB by id: " + id, e);
        }
    }

    private Manufacturer parseManufacturer(ResultSet resultSet) throws SQLException {
        Long manufacturerId = resultSet.getObject("id", Long.class);
        String manufacturerName = resultSet.getString("name");
        String manufacturerCountry = resultSet.getString("country");
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(manufacturerId);
        manufacturer.setName(manufacturerName);
        manufacturer.setCountry(manufacturerCountry);
        return manufacturer;
    }
}
