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
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final String NAME = "name";
    private static final String COUNTRY = "country";
    private static final String ID = "Id";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertQuery = "INSERT INTO manufacturers(name, country) values(?,?);";

        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement createFormatStatement = connection.prepareStatement(insertQuery,
                         Statement.RETURN_GENERATED_KEYS)) {

            createFormatStatement.setString(1,manufacturer.getName());
            createFormatStatement.setString(2,manufacturer.getCountry());
            createFormatStatement.executeUpdate();

            ResultSet generatedKeys = createFormatStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                Long object = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(object);
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new RuntimeException("Can't insert manufacturer " + manufacturer, e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String query = "SELECT * FROM manufacturers "
                + "WHERE id = ? AND is_delete = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement formatStatement = connection.prepareStatement(query)) {

            formatStatement.setLong(1, id);
            ResultSet resultSet = formatStatement.executeQuery();
            resultSet.next();
            return Optional.of(getManufacturer(resultSet));
        } catch (SQLException e) {
            throw new RuntimeException("Can't get manufacturer by id: " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String query = "SELECT * FROM manufacturers "
                + "WHERE is_delete = FALSE;";

        try (Connection connection = ConnectionUtil.getConnection();
                  Statement getAllStatement = connection.createStatement()) {

            ResultSet resultSet = getAllStatement.executeQuery(query);
            while (resultSet.next()) {
                allManufacturers.add(getManufacturer(resultSet));
            }
            return allManufacturers;
        } catch (SQLException e) {
            throw new RuntimeException("Can't get all manufacturers from DB", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String query = "UPDATE manufacturers SET  name = ?, country = ? "
                + "WHERE id = ? AND is_delete = FALSE;";

        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement formatStatement = connection.prepareStatement(query)) {

            formatStatement.setString(1, manufacturer.getName());
            formatStatement.setString(2, manufacturer.getCountry());
            formatStatement.setObject(3, manufacturer.getId());
            formatStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new RuntimeException("Can't update manufacturer with id: " + manufacturer.getId()
                    + " name: " + manufacturer.getName()
                    + " country: " + manufacturer.getCountry(), e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteQuery = "UPDATE manufacturers SET is_delete = TRUE where id = ?;";

        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement formatStatement = connection.prepareStatement(deleteQuery,
                         Statement.RETURN_GENERATED_KEYS)) {

            formatStatement.setLong(1, id);
            return formatStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete manufacturer with id: " + id, e);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getObject(ID, Long.class));
        manufacturer.setName(resultSet.getString(NAME));
        manufacturer.setCountry(resultSet.getString(COUNTRY));
        return manufacturer;
    }
}
