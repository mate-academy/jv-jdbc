package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_COUNTRY = "country";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        if (manufacturer == null) {
            throw new RuntimeException("Manufacturer can not be null");
        }
        String createManufacturer = "INSERT INTO manufacturers (name, country) VALUES (?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(createManufacturer, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.executeUpdate();
            ResultSet generateKeys = preparedStatement.getGeneratedKeys();
            if (generateKeys.next()) {
                Long id = generateKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't create manufacturer in DB ", throwables);
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
            Manufacturer manufacturer = new Manufacturer();
            if (getManufacturer.next()) {
                manufacturer.setId(getManufacturer.getLong(1));
                manufacturer.setName(getManufacturer.getString(2));
                manufacturer.setCountry(getManufacturer.getString(3));
            }
            manufacturerOptional = Optional.ofNullable(manufacturer);
        } catch (SQLException throwables) {
            throw new RuntimeException("Can not get manufacturer with ID = " + id + " from DB.",
                    throwables);
        }
        return manufacturerOptional;
    }

    @Override
    public List<Manufacturer> getAll() {
        String selectAllManufacturers = "SELECT * FROM manufacturers WHERE is_deleted = FALSE";
        List<Manufacturer> manufacturerList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(selectAllManufacturers)) {
            ResultSet getAllManufacturers = preparedStatement.executeQuery();
            while (getAllManufacturers.next()) {
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(getAllManufacturers.getObject(1, Long.class));
                manufacturer.setName(getAllManufacturers.getString(COLUMN_NAME));
                manufacturer.setCountry(getAllManufacturers.getString(COLUMN_COUNTRY));
                manufacturerList.add(manufacturer);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Can not to getAll manufacturers ", ex);
        }
        return manufacturerList;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        if (manufacturer == null) {
            throw new RuntimeException("id can not be null. Try again please.");
        }
        String query = "UPDATE manufacturers SET name = ?, country = ? WHERE id = ? "
                + "AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.setLong(3, manufacturer.getId());
            if (preparedStatement.executeUpdate() >= 1) {
                return manufacturer;
            }
        } catch (SQLException throwables) {
            throw new RuntimeException("Can not to update manufacturer with ID = "
                    + manufacturer.getId() + " in DB.", throwables);
        }
        throw new RuntimeException("There is no manufacturer with Id = "
                + manufacturer.getId() + " in DB.");
    }

    @Override
    public boolean delete(Long id) {
        if (id <= 0) {
            throw new RuntimeException("id can not be less than 0 or equal 0. Try again please.");
        }
        String deleteManufacturer = "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ? "
                + "AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(deleteManufacturer)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Can not delete manufacturer with ID = "
                    + id + " in DB ", e);
        }
    }
}
