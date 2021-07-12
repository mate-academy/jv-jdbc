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
import mate.jdbc.throwable.DataProcessingException;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final int INDEX_ONE = 1;
    private static final int INDEX_TWO = 2;
    private static final int INDEX_THREE = 3;
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String COUNTRY = "country";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String createManufacturerRequest =
                "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                   PreparedStatement createManufacturersStatement =
                           connection.prepareStatement(createManufacturerRequest,
                             Statement.RETURN_GENERATED_KEYS)) {
            createManufacturersStatement.setString(INDEX_ONE, manufacturer.getName());
            createManufacturersStatement.setString(INDEX_TWO, manufacturer.getCountry());
            createManufacturersStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturersStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(INDEX_ONE, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert a manufacturer to DB "
                    + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturerRequest =
                "SELECT * FROM manufacturers WHERE is_deleted = FALSE HAVING ID = ?;";
        Optional<Manufacturer> manufacturer = Optional.empty();
        try (Connection connection = ConnectionUtil.getConnection();
                   PreparedStatement getManufacturerStatement =
                        connection.prepareStatement(getManufacturerRequest)) {
            getManufacturerStatement.setLong(INDEX_ONE, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            if (resultSet.next()) {
                manufacturer = Optional.of(getManufacturerFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get a manufacturer from DB by id " + id, e);
        }
        return manufacturer;
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllManufacturersRequest =
                "SELECT * FROM manufacturers WHERE is_deleted = FALSE;";
        List<Manufacturer> manufacturerList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                   PreparedStatement getAllManufacturersStatement =
                        connection.prepareStatement(getAllManufacturersRequest)) {
            ResultSet resultSet = getAllManufacturersStatement.executeQuery();
            while (resultSet.next()) {
                manufacturerList.add(getManufacturerFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
        return manufacturerList;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerRequest =
                "UPDATE manufacturers SET name = ?, country = ? "
                + "WHERE (id = ? AND is_deleted = FALSE)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturersStatement =
                        connection.prepareStatement(updateManufacturerRequest,
                             Statement.RETURN_GENERATED_KEYS)) {
            createManufacturersStatement.setString(INDEX_ONE, manufacturer.getName());
            createManufacturersStatement.setString(INDEX_TWO, manufacturer.getCountry());
            createManufacturersStatement.setLong(INDEX_THREE, manufacturer.getId());
            createManufacturersStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturersStatement.getGeneratedKeys();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update a manufacturer in DB "
                    + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String safeDeleteManufacturerRequest =
                "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ?;";
        int result;
        try (Connection connection = ConnectionUtil.getConnection();
                   PreparedStatement safeDeleteManufacturerStatement =
                        connection.prepareStatement(safeDeleteManufacturerRequest)) {
            safeDeleteManufacturerStatement.setLong(INDEX_ONE, id);
            result = safeDeleteManufacturerStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete a manufacturer from DB by id " + id, e);
        }
        return result > 0;
    }

    private Manufacturer getManufacturerFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject(ID, Long.class);
        String name = resultSet.getString(NAME);
        String country = resultSet.getString(COUNTRY);
        Manufacturer manufacturer = new Manufacturer(name, country);
        manufacturer.setId(id);
        return manufacturer;
    }
}
