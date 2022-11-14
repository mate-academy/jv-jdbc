package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.data.processing.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final int FIRST_ELEMENT_IN_LIST = 0;
    private static final int FIRST_PARAMETER = 1;
    private static final int SECOND_PARAMETER = 2;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String query = "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(FIRST_PARAMETER, manufacturer.getName());
            statement.setString(SECOND_PARAMETER, manufacturer.getCountry());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(FIRST_PARAMETER, Long.class);
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
            statement.setLong(FIRST_PARAMETER, id);
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
        String query = "UPDATE manufacturers SET country = ? WHERE name = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(FIRST_PARAMETER, manufacturer.getCountry());
            statement.setString(SECOND_PARAMETER, manufacturer.getName());
            statement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new RuntimeException("Can't update current manufacturer: "
                    + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(FIRST_PARAMETER, id);
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
