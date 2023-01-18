package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.exception.DataProgressingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertRequest = "INSERT INTO manufacturers (name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturersStatement = connection
                        .prepareStatement(insertRequest, Statement.RETURN_GENERATED_KEYS)) {
            createManufacturersStatement.setString(1, manufacturer.getName());
            createManufacturersStatement.setString(2, manufacturer.getCountry());
            createManufacturersStatement.executeUpdate();
            ResultSet generatedKey = createManufacturersStatement.getGeneratedKeys();
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
        String insertRequest = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getManufacturersStatement = connection
                         .prepareStatement(insertRequest)) {
            getManufacturersStatement.setLong(1, id);
            ResultSet resultSet = getManufacturersStatement.executeQuery();
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
        String insertRequest = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getAllManufacturersStatement = connection
                         .prepareStatement(insertRequest)) {
            ResultSet resultSet = getAllManufacturersStatement.executeQuery();
            if (resultSet == null) {
                return allManufacturers;
            }
            while (resultSet.next()) {
                if (!resultSet.getBoolean("is_deleted")) {
                    Manufacturer manufacturer = getManufacturer(resultSet);
                    allManufacturers.add(manufacturer);
                }
            }
            return allManufacturers;
        } catch (SQLException e) {
            throw new DataProgressingException("Can't get all data"
                    + " from manufacturer table", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String insertRequest = "UPDATE manufacturers SET name = ?, country "
                + "= ? WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement updateManufacturersStatement = connection
                         .prepareStatement(insertRequest)) {
            updateManufacturersStatement.setLong(3, manufacturer.getId());
            updateManufacturersStatement.setString(1, manufacturer.getName());
            updateManufacturersStatement.setString(2, manufacturer.getCountry());
            updateManufacturersStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProgressingException("Can't update manufacturer "
                    + "from manufacturer table with ID: " + manufacturer.getId(), e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String insertRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement deleteManufacturersStatement = connection
                         .prepareStatement(insertRequest)) {
            deleteManufacturersStatement.setLong(1, id);
            return deleteManufacturersStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProgressingException("Can't delete manufacturer "
                    + "from manufacturer table by ID: " + id, e);
        }
    }

    private static Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(id);
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        return manufacturer;
    }
}
