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
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    public static final String ID_COLUMN = "id";
    public static final String NANE_COLUMN = "name";
    public static final String COUNTRY_COLUMN = "country";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String queryCreateManufacturer = "INSERT INTO manufacturers (name, country) "
                + "VALUES (?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
                    PreparedStatement createManufacturerStatement
                            = connection.prepareStatement(queryCreateManufacturer,
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
            throw new DataProcessingException("Can't create manufacturer "
                    + manufacturer + " to DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Optional<Manufacturer> result = Optional.empty();
        String queryGetManufacturer = "SELECT * FROM manufacturers "
                + "WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getManufacturerStatement
                         = connection.prepareStatement(queryGetManufacturer)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            if (resultSet.next()) {
                result = Optional.of(getManufacturerFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by id = "
                    + id + " from DB", e);
        }
        return result;
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> result = new ArrayList<>();
        String queryAllManufacturers = "SELECT * FROM manufacturers WHERE is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement allManufacturersStatement
                        = connection.prepareStatement(queryAllManufacturers)) {
            ResultSet resultSet = allManufacturersStatement.executeQuery();
            while (resultSet.next()) {
                result.add(getManufacturerFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
        return result;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String queryUpdateManufacturer = "UPDATE manufacturers SET name = ?, country = ? "
                + "WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement updateManufacturerStatement
                        = connection.prepareStatement(queryUpdateManufacturer)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            if (updateManufacturerStatement.executeUpdate() > 0) {
                return manufacturer;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer "
                    + manufacturer + " to DB", e);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        String queryDeleteManufacturer = "UPDATE manufacturers SET is_deleted = TRUE "
                + "WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement deleteManufacturerStatement
                        = connection.prepareStatement(queryDeleteManufacturer)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer by id = "
                    + id + " in DB", e);
        }
    }

    private Manufacturer getManufacturerFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject(ID_COLUMN, Long.class);
        String name = resultSet.getString(NANE_COLUMN);
        String country = resultSet.getString(COUNTRY_COLUMN);
        return new Manufacturer(id, name, country);
    }
}
