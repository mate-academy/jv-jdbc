package mate.jdbc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.exception.DataProcessException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest = "INSERT INTO manufacturers (name, country) "
                + "values (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturer =
                        connection.prepareStatement(insertManufacturerRequest,
                             Statement.RETURN_GENERATED_KEYS)) {
            createManufacturer.setString(1, manufacturer.getName());
            createManufacturer.setString(2, manufacturer.getCountry());
            createManufacturer.executeUpdate();
            ResultSet generatedKeys = createManufacturer.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessException("Can't create new Manufacturer", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String selectByIdRequest =
                "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = 'false';";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getSelectedManufacturer
                        = connection.prepareStatement(selectByIdRequest)) {
            getSelectedManufacturer.setLong(1, id);
            ResultSet resultSet = getSelectedManufacturer.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new Manufacturer(id,
                        resultSet.getString("name"),
                        resultSet.getString("country")));
            }

        } catch (SQLException e) {
            throw new DataProcessException("Can't get the item", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String selectAllRequest = "SELECT * FROM manufacturers WHERE is_deleted = 'false';";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturers
                        = connection.prepareStatement(selectAllRequest)) {
            ResultSet resultSet = getAllManufacturers.executeQuery();
            while (resultSet.next()) {
                allManufacturers.add(new Manufacturer(resultSet.getObject("id", Long.class),
                        resultSet.getString("name"),
                        resultSet.getString("country")));
            }
        } catch (SQLException e) {
            throw new DataProcessException("Can't get all manufacturers from DB", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers Set name = ?, country = ? "
                + "WHERE id = ? AND is_deleted = 'false';";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturer = connection.prepareStatement(updateRequest)) {
            updateManufacturer.setString(1, manufacturer.getName());
            updateManufacturer.setString(2, manufacturer.getCountry());
            updateManufacturer.setLong(3, manufacturer.getId());
            updateManufacturer.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessException("Can't update the manufacturer", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = 'true'"
                + " WHERE id =? AND is_deleted = 'false';";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturer = connection.prepareStatement(deleteRequest)) {
            deleteManufacturer.setLong(1, id);
            return deleteManufacturer.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessException("Can't insert format to DB", e);
        }
    }
}
