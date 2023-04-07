package mate.jdbc.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.DataProcessingException;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertDataRequest = "INSERT INTO manufacturers(name, country) VALUES(?, ?)";
        try (PreparedStatement createDataStatement = ConnectionUtil.getConnection()
                .prepareStatement(insertDataRequest, Statement.RETURN_GENERATED_KEYS)) {
            createDataStatement.setString(1, manufacturer.getName());
            createDataStatement.setString(2, manufacturer.getCountry());
            createDataStatement.executeUpdate();
            ResultSet generatedKeys = createDataStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert data to DB: " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getRequest = "SELECT * FROM manufacturers WHERE id = ?";
        try (PreparedStatement getDataStatement = ConnectionUtil.getConnection()
                .prepareStatement(getRequest)) {
            getDataStatement.setLong(1, id);
            ResultSet result = getDataStatement.executeQuery();
            if (result.next()) {
                Manufacturer manufacturer = new Manufacturer();
                return Optional.of(parseManufacturer(result));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get data from DB with using the id - "
                    + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String getAllRequest = "SELECT * FROM manufacturers WHERE is_deleted = FALSE";
        try (PreparedStatement getAllFormatsStatement = ConnectionUtil
                .getConnection().prepareStatement(getAllRequest)) {
            ResultSet resultSet = getAllFormatsStatement.executeQuery();
            while (resultSet.next()) {
                Manufacturer manufacturer = new Manufacturer();
                allManufacturers.add(parseManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all formats from DB", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET name = ?, country = ? WHERE id = ?";
        try (PreparedStatement updateDataStatement = ConnectionUtil.getConnection()
                .prepareStatement(updateRequest)) {
            updateDataStatement.setString(1, manufacturer.getName());
            updateDataStatement.setString(2, manufacturer.getCountry());
            updateDataStatement.setLong(3, manufacturer.getId());
            updateDataStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update data in DB: " + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ?";
        try (PreparedStatement createDataStatement = ConnectionUtil.getConnection()
                .prepareStatement(deleteRequest)) {
            createDataStatement.setLong(1, id);
            return createDataStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer from DB with using id - "
                    + id, e);
        }
    }

    private Manufacturer parseManufacturer(ResultSet resultSet)
            throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        return new Manufacturer(id, name, country);
    }
}
