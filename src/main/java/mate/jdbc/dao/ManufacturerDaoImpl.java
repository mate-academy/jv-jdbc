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
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertRequest = "INSERT INTO manufacturers (name, country)"
                + " VALUES (?, ?);";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createMfStatement =
                        connection.prepareStatement(insertRequest,
                             Statement.RETURN_GENERATED_KEYS)) {
            createMfStatement.setString(1, manufacturer.getName());
            createMfStatement.setString(2, manufacturer.getCountry());
            createMfStatement.executeUpdate();
            ResultSet generatedKeys = createMfStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create new entry to [manufacturers] for "
                    + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String getAllRequest = "SELECT * FROM manufacturers"
                + " WHERE is_deleted = FALSE";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement allMfStatement =
                        connection.prepareStatement(getAllRequest)) {
            ResultSet resultSet = allMfStatement.executeQuery();

            while (resultSet.next()) {
                allManufacturers.add(parseManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't inquiry [manufacturers] SQL database", e);
        }
        return allManufacturers;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getRequest = "SELECT * FROM manufacturers"
                + " WHERE id = ? AND is_deleted = FALSE";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement selectMfByIdStatement =
                        connection
                             .prepareStatement(getRequest)) {
            selectMfByIdStatement.setLong(1, id);
            ResultSet resultSet = selectMfByIdStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(parseManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't obtain data from [manufacturers] "
                    + "SQL database for id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET name = ?,"
                + " country = ? WHERE id = ? AND is_deleted = FALSE";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateMfStatement =
                        connection.prepareStatement(updateRequest)) {

            updateMfStatement.setString(1, manufacturer.getName());
            updateMfStatement.setString(2, manufacturer.getCountry());
            updateMfStatement.setLong(3, manufacturer.getId());
            updateMfStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update entry from [manufacturers] SQL "
                    + "database for manufacturer" + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET"
                + " is_deleted = TRUE WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement =
                        connection.prepareStatement(deleteRequest)) {
            deleteManufacturerStatement.setLong(1, id);

            return deleteManufacturerStatement.executeUpdate() >= 1;

        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete entry from [manufacturers] "
                    + "SQL database for id: " + id, e);
        }
    }

    private Manufacturer parseManufacturer(ResultSet resultSet) throws SQLException {
        return new Manufacturer(
                resultSet.getObject("id", Long.class),
                resultSet.getString("name"),
                resultSet.getString("country")
        );
    }
}
