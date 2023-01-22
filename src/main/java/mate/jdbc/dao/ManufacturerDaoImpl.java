package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final int FIRST_ELEMENT_IN_LIST = 0;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String query = "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer to DB. Manufacturer name: "
                    + manufacturer.getName(), e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String query = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            List<Manufacturer> manufacturers = getManufacturersFromResultSet(statement);
            return Optional.ofNullable(manufacturers.get(FIRST_ELEMENT_IN_LIST));
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by id " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String query = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            return getManufacturersFromResultSet(statement);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB ", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String stringRequest = "UPDATE manufacturers SET name = ?, "
                + "country = ? WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection
                         .prepareStatement(stringRequest)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.setLong(3, manufacturer.getId());
            if (statement.executeUpdate() > 0) {
                return manufacturer;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update current manufacturer: "
                    + manufacturer, e);
        }
        throw new NoSuchElementException("Can't find " + manufacturer);
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete manufacturer with id " + id, e);
        }
    }

    private List<Manufacturer> getManufacturersFromResultSet(PreparedStatement statement) {
        List<Manufacturer> manufacturers = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery();
            Manufacturer manufacturer = null;
            while (resultSet.next()) {
                manufacturer = new Manufacturer(
                        resultSet.getObject("id", Long.class),
                        resultSet.getString("name"),
                        resultSet.getString("country"));
                manufacturers.add(manufacturer);
            }
            return manufacturers;
        } catch (SQLException e) {
            throw new RuntimeException("Can't get a manufacturer from the Result Set", e);
        }
    }
}
