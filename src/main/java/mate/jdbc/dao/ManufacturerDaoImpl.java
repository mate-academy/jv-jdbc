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
    private static final String NAME = "name";
    private static final String COUNTRY = "country";
    private static final String ID = "id";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String query = "INSERT INTO manufacturer (name, country, is_deleted) VALUES (?, ?, false)";
        try (Connection connection = ConnectionUtil.getConnection();
                    PreparedStatement createNewManufacturer = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)) {
            createNewManufacturer.setString(1, manufacturer.getName());
            createNewManufacturer.setString(2, manufacturer.getCountry());
            createNewManufacturer.executeUpdate();
            ResultSet generatedKeys = createNewManufacturer.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create field in manufactures table", e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String query = "SELECT * FROM manufacturer WHERE id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getManufacturerById = connection.prepareStatement(query)) {
            getManufacturerById.setLong(1, id);
            ResultSet resultSet = getManufacturerById.executeQuery();
            return Optional.of(getManufacturerFromResultSet(resultSet));
        } catch (SQLException e) {
            throw new DataProcessingException("This field doesn't exist", e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> result = new ArrayList<>();
        String query = "SELECT * FROM manufacturer WHERE is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement get = connection.prepareStatement(query)) {
            get.execute();
            ResultSet resultSet = get.getResultSet();
            while (resultSet.next()) {
                result.add(getManufacturerFromResultSet(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get data from db", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String query = "UPDATE manufacturer SET name = ?, country = ?"
                + " WHERE id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateField = connection.prepareStatement(query)) {
            updateField.setString(1, manufacturer.getName());
            updateField.setString(2, manufacturer.getCountry());
            updateField.setLong(3, manufacturer.getId());
            updateField.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update field with id: "
                    + manufacturer.getId(), e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE manufacturer SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement deleteById = connection.prepareStatement(query)) {
            deleteById.setLong(1, id);
            return deleteById.execute();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't remove data from db id: " + id, e);
        }
    }

    private Manufacturer getManufacturerFromResultSet(ResultSet manufacturer) throws SQLException {
        Long id = manufacturer.getObject(ID, Long.class);
        String name = manufacturer.getString(NAME);
        String country = manufacturer.getString(COUNTRY);
        return new Manufacturer(id, name, country);
    }
}
