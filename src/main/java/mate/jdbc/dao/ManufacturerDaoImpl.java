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
        String insertFormatRequest = "INSERT INTO manufacturers (name, country) VALUES (?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createFormatStatement = connection
                        .prepareStatement(insertFormatRequest, Statement.RETURN_GENERATED_KEYS)) {
            createFormatStatement.setString(1, manufacturer.getName());
            createFormatStatement.setString(2, manufacturer.getCountry());
            createFormatStatement.executeUpdate();
            ResultSet generatedKeys = createFormatStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not insert info to DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getRequest = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                   PreparedStatement createFormatStatement =
                        connection.prepareStatement(getRequest)) {
            createFormatStatement.setLong(1, id);
            ResultSet resultSet = createFormatStatement.executeQuery();
            resultSet.next();
            Manufacturer manufacturer = getManufacturer(resultSet);
            return Optional.of(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get info by id: " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllRequests = "SELECT * FROM manufacturers WHERE is_deleted = FALSE";
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createFormatStatement =
                        connection.prepareStatement(getAllRequests)) {
            ResultSet resultSet = createFormatStatement
                    .executeQuery(getAllRequests);
            while (resultSet.next()) {
                Manufacturer manufacturer = getManufacturer(resultSet);
                manufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get all info from DB", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String deleteRequest = "UPDATE manufacturers SET name = ?, country "
                + "= ? WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createFormatStatement =
                        connection.prepareStatement(deleteRequest)) {

            createFormatStatement.setString(1, manufacturer.getName());
            createFormatStatement.setString(2, manufacturer.getCountry());
            createFormatStatement.setLong(3, manufacturer.getId());
            int i = createFormatStatement.executeUpdate();
            if (i == 0) {
                throw new RuntimeException("id do not exist. id: " + manufacturer.getId());
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not update info from DB by id: "
                    + manufacturer.getId(), e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createFormatStatement =
                        connection.prepareStatement(deleteRequest)) {

            createFormatStatement.setLong(1, id);

            return createFormatStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not delete info from DB by id: " + id, e);
        }
    }

    public Manufacturer getManufacturer(ResultSet resultSet) {
        try {
            Long id = resultSet.getObject("id", Long.class);
            String name = resultSet.getString("name");
            String country = resultSet.getString("country");
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setId(id);
            manufacturer.setName(name);
            manufacturer.setCountry(country);
            return manufacturer;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
