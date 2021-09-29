package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        if (manufacturer == null) {
            throw new RuntimeException("Manufacturer can not be null");
        }
        String createManufacturer = "INSERT INTO manufacturers (name, country) VALUES (?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(createManufacturer)) {
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't create Manufacturer in DB ", throwables);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        if (id == null) {
            throw new RuntimeException("id can not be null. Try again please.");
        }
        String query = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = FALSE";
        Optional<Manufacturer> manufacturerOptional;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet getManufacturer = preparedStatement.executeQuery();
            getManufacturer.next();
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setId(getManufacturer.getLong(1));
            manufacturer.setName(getManufacturer.getString(2));
            manufacturer.setCountry(getManufacturer.getString(3));
            manufacturerOptional = Optional.ofNullable(manufacturer);
        } catch (SQLException throwables) {
            throw new RuntimeException("Can not get Manufacturer with ID = " + id + " from DB.",
                    throwables);
        }
        return manufacturerOptional;
    }

    @Override
    public List<Manufacturer> getAll() {
        String query = "SELECT * FROM manufacturers WHERE is_deleted = FALSE";
        List<Manufacturer> manufacturerList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet getAllManufacturers = preparedStatement.executeQuery();
            while (getAllManufacturers.next()) {
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(getAllManufacturers.getLong("id"));
                manufacturer.setName(getAllManufacturers.getString("name"));
                manufacturer.setCountry(getAllManufacturers.getString("country"));
                manufacturerList.add(manufacturer);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Can not to getAll  manufacturers ", ex);
        }
        return manufacturerList;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        if (manufacturer == null) {
            throw new RuntimeException("id can not be null. Try again please.");
        }
        String query = "UPDATE manufacturers SET name = ?, country = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.setLong(3, manufacturer.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new RuntimeException("Can not to update manufacturer with ID = "
                    + manufacturer.getId() + " in DB.", throwables);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        if (id <= 0) {
            throw new RuntimeException("id can not be less than 0 or equal 0. Try again please.");
        }
        String query = "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Can not delete Manufacturer with ID = "
                    + id + " in DB ", e);
        }
    }
}
