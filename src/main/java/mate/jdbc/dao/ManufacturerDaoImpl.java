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
import mate.jdbc.util.ConnectivityUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final int NAME_MANUFACTURER = 1;
    private static final int COUNTRY_MANUFACTURER = 2;
    private static final int ID_MANUFACTURER = 3;

    @Override
    public List<Manufacturer> getAll() {
        String query = "SELECT * FROM manufacturer WHERE is_deleted = false";
        List<Manufacturer> manufacturers = new ArrayList<>();

        try (Connection connection = ConnectivityUtil.getConnection();
                PreparedStatement getAllStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = getAllStatement.executeQuery();
            while (resultSet.next()) {
                manufacturers.add(getManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cant get all manufacturer from DB", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest = "INSERT INTO manufacturer(name, country) values(?, ?);";

        try (Connection connection = ConnectivityUtil.getConnection();
                PreparedStatement createManufacturerStatement = connection
                        .prepareStatement(insertManufacturerRequest,
                                Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(NAME_MANUFACTURER, manufacturer.getName());
            createManufacturerStatement.setString(COUNTRY_MANUFACTURER, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(NAME_MANUFACTURER, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create manufacturer " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getRequest = "SELECT * FROM manufacturer WHERE id = ? AND is_deleted = false";

        try (Connection connection = ConnectivityUtil.getConnection();

                PreparedStatement getManufacturerStatement = connection
                        .prepareStatement(getRequest)) {
            getManufacturerStatement.setLong(NAME_MANUFACTURER, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            Manufacturer manufacturer = null;
            if (resultSet.next()) {
                manufacturer = getManufacturer(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer from DB by id: " + id, e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturer SET name = ?, country = ?"
                + " where id = ? AND is_deleted = false";
        try (Connection connection = ConnectivityUtil.getConnection();
                PreparedStatement updateStatement = connection.prepareStatement(updateRequest)) {
            updateStatement.setString(NAME_MANUFACTURER, manufacturer.getName());
            updateStatement.setString(COUNTRY_MANUFACTURER, manufacturer.getCountry());
            updateStatement.setLong(ID_MANUFACTURER, manufacturer.getId());
            updateStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer "
                    + manufacturer + " from DB", e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturer SET is_deleted = true where id = ?";

        try (Connection connection = ConnectivityUtil.getConnection();
                PreparedStatement deleteManufacturersStatement = connection
                        .prepareStatement(deleteRequest)) {
            deleteManufacturersStatement.setLong(NAME_MANUFACTURER, id);
            return deleteManufacturersStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer from DB by id " + id, e);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getObject("id", Long.class));
        manufacturer.setName(resultSet.getString("name"));
        manufacturer.setCountry(resultSet.getString("country"));
        return manufacturer;
    }
}
