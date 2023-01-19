package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import mate.jdbc.exception.DataProgressingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final Map<Manufacturer.Operation, String> request = new HashMap<>();

    static {
        request.put(Manufacturer.Operation.CREATE, "INSERT INTO manufacturers "
                + "(name, country) values(?, ?);");
        request.put(Manufacturer.Operation.DELETE, "UPDATE manufacturers SET "
                + "is_deleted = true WHERE id = ?;");
        request.put(Manufacturer.Operation.GET, "SELECT * FROM manufacturers "
                + "WHERE id = ? AND is_deleted = false;");
        request.put(Manufacturer.Operation.GET_ALL, "SELECT * FROM manufacturers "
                + "WHERE is_deleted = false;");
        request.put(Manufacturer.Operation.UPDATE, "UPDATE manufacturers SET name = ?, "
                + "country = ? WHERE id = ? AND is_deleted = false;");
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createStatement = connection
                        .prepareStatement(request.get(Manufacturer.Operation.CREATE),
                                Statement.RETURN_GENERATED_KEYS)) {
            createStatement.setString(1, manufacturer.getName());
            createStatement.setString(2, manufacturer.getCountry());
            createStatement.executeUpdate();
            ResultSet generatedKey = createStatement.getGeneratedKeys();
            if (generatedKey.next()) {
                Long id = generatedKey.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProgressingException("Can't create manufacturer "
                    + "in manufacturer table: " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getStatement = connection
                         .prepareStatement(request.get(Manufacturer.Operation.GET))) {
            getStatement.setLong(1, id);
            ResultSet resultSet = getStatement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }
            return Optional.of(getManufacturer(resultSet));
        } catch (SQLException e) {
            throw new DataProgressingException("Can't get manufacturer "
                    + "from manufacturer table by ID: " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getAllStatement = connection
                         .prepareStatement(request.get(Manufacturer.Operation.GET_ALL))) {
            ResultSet resultSet = getAllStatement.executeQuery();
            if (resultSet == null) {
                return allManufacturers;
            }
            while (resultSet.next()) {
                Manufacturer manufacturer = getManufacturer(resultSet);
                allManufacturers.add(manufacturer);
            }
            return allManufacturers;
        } catch (SQLException e) {
            throw new DataProgressingException("Can't get all data"
                    + " from manufacturer table", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement updateStatement = connection
                         .prepareStatement(request.get(Manufacturer.Operation.UPDATE))) {
            updateStatement.setLong(3, manufacturer.getId());
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProgressingException("Can't update manufacturer "
                    + "from manufacturer table with ID: " + manufacturer.getId(), e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement deleteStatement = connection
                         .prepareStatement(request.get(Manufacturer.Operation.DELETE))) {
            deleteStatement.setLong(1, id);
            return deleteStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProgressingException("Can't delete manufacturer "
                    + "from manufacturer table by ID: " + id, e);
        }
    }

    private static Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        return new Manufacturer(id, name, country);
    }
}
