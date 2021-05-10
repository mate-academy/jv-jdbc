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
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final String TABLE_NAME = "manufacturers";
    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";
    private static final String COUNTRY_COLUMN = "country";
    private static final String IS_DELETED_COLUMN = "is_deleted";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertRequest = "INSERT INTO " + TABLE_NAME + "("
                + NAME_COLUMN + ',' + COUNTRY_COLUMN + ") VALUES(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement insertManufacturer = connection.prepareStatement(insertRequest,
                        Statement.RETURN_GENERATED_KEYS)) {
            insertManufacturer.setString(1, manufacturer.getName());
            insertManufacturer.setString(2, manufacturer.getCountry());
            insertManufacturer.executeUpdate();
            ResultSet generatedKeys = insertManufacturer.getGeneratedKeys();
            if (generatedKeys.next()) {
                manufacturer.setId(generatedKeys.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new RuntimeException(
                    "Cant insert name: " + manufacturer.getId() + " to db", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String selectFromRequest = "SELECT * FROM " + TABLE_NAME
                + " WHERE " + ID_COLUMN + " = " + id + "AND"
                + IS_DELETED_COLUMN + "= false";
        Manufacturer manufacturer = null;
        try (Connection connection = ConnectionUtil.getConnection(); Statement getManufacturer
                = connection.createStatement()) {
            ResultSet resultSet = getManufacturer.executeQuery(selectFromRequest);
            if (resultSet.next()) {
                manufacturer = makeManufacturer(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new RuntimeException("Cant update manufacturers in db", e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String selectFromRequest = "SELECT * FROM " + TABLE_NAME
                + " WHERE " + IS_DELETED_COLUMN + "= false";
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllManufacturers = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturers
                    .executeQuery(selectFromRequest);
            while (resultSet.next()) {
                manufacturers.add(makeManufacturer(resultSet));
            }
            return manufacturers;
        } catch (SQLException e) {
            throw new RuntimeException("Cant get all manufacturers from db", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE " + TABLE_NAME
                + " SET " + NAME_COLUMN + " = ?,"
                + COUNTRY_COLUMN + " = ? "
                + "WHERE " + ID_COLUMN + "= ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement updateManufacturer
                         = connection.prepareStatement(updateRequest)) {
            updateManufacturer.setString(1, manufacturer.getName());
            updateManufacturer.setString(2, manufacturer.getCountry());
            updateManufacturer.setObject(3, manufacturer.getId());
            updateManufacturer.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new RuntimeException("Cant update id: " + manufacturer.getId() + " in db", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE " + TABLE_NAME
                + " SET " + IS_DELETED_COLUMN + " = true WHERE "
                + ID_COLUMN + " = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement deletedManufacturers
                         = connection.prepareStatement(deleteRequest)) {
            deletedManufacturers.setLong(1, id);
            return deletedManufacturers.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new RuntimeException("Cant delete id: " + id + " in db", e);
        }
    }

    private Manufacturer makeManufacturer(ResultSet resultSet)
            throws SQLException {
        Long id = resultSet.getLong(ID_COLUMN);
        String name = resultSet.getString(NAME_COLUMN);
        String country = resultSet.getString(COUNTRY_COLUMN);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(id);
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        return manufacturer;
    }
}
