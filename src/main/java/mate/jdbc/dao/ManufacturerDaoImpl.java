package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.exceptions.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertFormatStatement = "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                    PreparedStatement createFormatStatement = connection
                             .prepareStatement(insertFormatStatement,
                                     Statement.RETURN_GENERATED_KEYS)) {
            createFormatStatement.setString(1, manufacturer.getName());
            createFormatStatement.setString(2, manufacturer.getCountry());
            createFormatStatement.executeUpdate();
            ResultSet generatedKeys = createFormatStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long manufacturerId = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(manufacturerId);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer "
                    + manufacturer + " to DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getIdResultRequest = "SELECT * FROM manufacturers "
                + "WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                     PreparedStatement getIdResultStatement = connection
                             .prepareStatement(getIdResultRequest)) {
            getIdResultStatement.setLong(1, id);
            ResultSet resultSet = getIdResultStatement.executeQuery();
            Manufacturer manufacturer = null;
            if (resultSet.next()) {
                manufacturer = getManufacturer(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by id " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String getAllResultRequest = "SELECT * FROM manufacturers WHERE is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                     PreparedStatement getAllResultStatement = connection
                             .prepareStatement(getAllResultRequest)) {
            ResultSet resultSet = getAllResultStatement.executeQuery();
            while (resultSet.next()) {
                Manufacturer manufacturer = getManufacturer(resultSet);
                manufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String insertFormatStatement = "UPDATE manufacturers SET name = ?, "
                + "country = ? WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                     PreparedStatement updateFormatStatement = connection
                             .prepareStatement(insertFormatStatement)) {
            updateFormatStatement.setString(1, manufacturer.getName());
            updateFormatStatement.setString(2, manufacturer.getCountry());
            updateFormatStatement.setLong(3, manufacturer.getId());
            updateFormatStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer "
                    + manufacturer + " in DB", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                     PreparedStatement deleteFormatStatement = connection
                             .prepareStatement(deleteRequest)) {
            deleteFormatStatement.setLong(1, id);
            return deleteFormatStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer by id "
                    + id + " from DB", e);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        Long manufacturerId = resultSet.getObject("id", Long.class);
        String manufacturerName = resultSet.getString("name");
        String manufacturerCountry = resultSet.getString("country");
        Manufacturer manufacturer
                = new Manufacturer(manufacturerName, manufacturerCountry);
        manufacturer.setId(manufacturerId);
        return manufacturer;
    }
}
