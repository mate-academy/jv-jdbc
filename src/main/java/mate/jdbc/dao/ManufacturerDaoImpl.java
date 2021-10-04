package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.exeption.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertRequest = "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatment = connection
                        .prepareStatement(insertRequest, Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatment.setString(1, manufacturer.getName());
            createManufacturerStatment.setString(2, manufacturer.getCountry());
            createManufacturerStatment.executeUpdate();
            ResultSet generetedKeys = createManufacturerStatment.getGeneratedKeys();
            if (generetedKeys.next()) {
                Long id = generetedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't insert manufacturer to DB", throwables);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getRequest = "SELECT * FROM manufacturers WHERE is_deleted = FALSE AND id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatment = connection
                        .prepareStatement(getRequest)) {
            getManufacturerStatment.setLong(1, id);
            ResultSet resultSet = getManufacturerStatment.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Manufacturer manufacturer = new Manufacturer(id, name, country);
                return Optional.of(manufacturer);
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't get manufacturer with id: "
                    + id + " from DB", throwables);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllRequest = "SELECT * FROM manufacturers WHERE is_deleted = FALSE;";
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllManufacturersStatment = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturersStatment
                    .executeQuery(getAllRequest);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Long id = resultSet.getObject("id", Long.class);
                Manufacturer manufacturer = new Manufacturer(id, name, country);
                manufacturers.add(manufacturer);
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't get all manufacturers from DB", throwables);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET name = ?, country = ?"
                + "WHERE is_deleted = FALSE AND id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatment = connection
                        .prepareStatement(updateRequest)) {
            updateManufacturerStatment.setString(1, manufacturer.getName());
            updateManufacturerStatment.setString(2, manufacturer.getCountry());
            updateManufacturerStatment.setLong(3, manufacturer.getId());
            updateManufacturerStatment.executeUpdate();
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't update manufacturer in DB", throwables);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatment = connection
                        .prepareStatement(deleteRequest, Statement.RETURN_GENERATED_KEYS)) {
            deleteManufacturerStatment.setLong(1, id);
            return deleteManufacturerStatment.executeUpdate() >= 1;
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't delete manufacturer from DB", throwables);
        }
    }
}
