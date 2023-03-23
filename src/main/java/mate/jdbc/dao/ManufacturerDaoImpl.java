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
    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";
    private static final String COUNTRY_COLUMN = "country";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturersRequest =
                "INSERT INTO manufacturers (name, country) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                     PreparedStatement createManufacturerStatement =
                          connection.prepareStatement(insertManufacturersRequest,
                             Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturers to DB" + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturerQuery =
                "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                    PreparedStatement getManufacturerStatement = connection
                            .prepareStatement(getManufacturerQuery)) {
            getManufacturerStatement.setLong(1, id);
            getManufacturerStatement.executeQuery();
            ResultSet resultSet = getManufacturerStatement.getResultSet();
            if (resultSet.next()) {
                return Optional.of(manufacturerResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by id", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllManufacturersQuery = "SELECT * FROM manufacturers WHERE is_deleted = FALSE";
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                     PreparedStatement getAllManufacturersStatement = connection
                          .prepareStatement(getAllManufacturersQuery)) {
            ResultSet resultSet = getAllManufacturersStatement
                    .executeQuery(getAllManufacturersQuery);
            while (resultSet.next()) {
                allManufacturers.add(manufacturerResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get all manufacturers from DB", e);
        }

        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateQuery = "UPDATE manufacturers "
                + "SET name = ?, country = ? WHERE (id = ?) AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                     PreparedStatement updateStatement = connection
                           .prepareStatement(updateQuery)) {
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.setLong(3, manufacturer.getId());
            if (updateStatement.executeUpdate() > 0) {
                return manufacturer;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer " + manufacturer, e);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        String deleteQuery = "UPDATE manufacturers SET is_deleted = "
                + "TRUE WHERE is_deleted = FALSE AND id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                     PreparedStatement deleteManufacturerStatement =
                           connection.prepareStatement(deleteQuery)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturers from DB " + id, e);
        }
    }

    private Manufacturer manufacturerResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject(ID_COLUMN, Long.class);
        String name = resultSet.getString(NAME_COLUMN);
        String country = resultSet.getString(COUNTRY_COLUMN);
        return new Manufacturer(id, name, country);
    }
}
