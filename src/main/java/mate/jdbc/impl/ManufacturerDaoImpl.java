package mate.jdbc.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.exceptions.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String createManufacturerRequest = "INSERT INTO manufacturers (name, country) "
                + "VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();PreparedStatement
                createManufacturerStatement = connection.prepareStatement(createManufacturerRequest,
                Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer: "
                    + manufacturer + " to database", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturerRequest = "SELECT * FROM manufacturers"
                + " WHERE id = " + id
                + " AND is_deleted = FALSE";

        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement
                getManufacturersStatement = connection.prepareStatement(getManufacturerRequest)) {
            ResultSet getManufacturer = getManufacturersStatement.executeQuery();
            if (getManufacturer.next()) {
                return Optional.of(retrievingDataFromResultSet(getManufacturer));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by id: " + id
                    + " from database", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllManufacturersRequest = "SELECT * FROM manufacturers "
                + "WHERE is_deleted = FALSE";
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement
                getAllManufacturersStatement =
                connection.prepareStatement(getAllManufacturersRequest)) {
            ResultSet getAllManufacturersResultSet = getAllManufacturersStatement
                    .executeQuery();
            while (getAllManufacturersResultSet.next()) {
                allManufacturers.add(retrievingDataFromResultSet(getAllManufacturersResultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from database", e);
        }

        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerRequest = "UPDATE manufacturers SET name = ?, country = ? "
                + "WHERE id = " + manufacturer.getId()
                + " AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement
                updateManufacturersStatement = connection
                .prepareStatement(updateManufacturerRequest)) {
            updateManufacturersStatement.setString(1, manufacturer.getName());
            updateManufacturersStatement.setString(2, manufacturer.getCountry());
            updateManufacturersStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer: " + manufacturer
                    + " in database", e);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufacturersRequest = "UPDATE manufacturers  SET is_deleted "
                + "= TRUE WHERE id = " + id;
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement
                deleteManufacturersStatement = connection
                .prepareStatement(deleteManufacturersRequest)) {
            return deleteManufacturersStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer by id: "
                    + id + " from database", e);
        }
    }

    private Manufacturer retrievingDataFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(id);
        manufacturer.setCountry(country);
        manufacturer.setName(name);
        return manufacturer;
    }
}
