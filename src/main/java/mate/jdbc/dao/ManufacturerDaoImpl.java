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
    private static final int PARAMETER_INDEX_1 = 1;
    private static final int PARAMETER_INDEX_2 = 2;
    private static final int PARAMETER_INDEX_3 = 3;
    private static final String COLUMN_LABEL_ID = "id";
    private static final String COLUMN_LABEL_NAME = "name";
    private static final String COLUMN_LABEL_COUNTRY = "country";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerQuery = "INSERT INTO manufacturers (name, country) VALUES(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement
                        = connection.prepareStatement(insertManufacturerQuery,
                           Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(PARAMETER_INDEX_1, manufacturer.getName());
            createManufacturerStatement.setString(PARAMETER_INDEX_2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKey = createManufacturerStatement.getGeneratedKeys();
            if (generatedKey.next()) {
                Long id = generatedKey.getObject(PARAMETER_INDEX_1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer to DB " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getByIdManufacturerQuery = "SELECT * FROM manufacturers "
                + "WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement
                        = connection.prepareStatement(getByIdManufacturerQuery)) {
            getManufacturerStatement.setLong(PARAMETER_INDEX_1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            Manufacturer manufacturer = null;
            if (resultSet.next()) {
                manufacturer = getManufacturer(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturers by id from DB " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String insertManufacturerQuery = "SELECT * FROM manufacturers WHERE is_deleted = FALSE;";
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                   PreparedStatement getAllManufacturerStatement
                           = connection.prepareStatement(insertManufacturerQuery)) {
            ResultSet resultSet
                    = getAllManufacturerStatement.executeQuery(insertManufacturerQuery);
            while (resultSet.next()) {
                manufacturers.add(getManufacturer(resultSet));
            }
            return manufacturers;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB ", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateQuery = "UPDATE manufacturers SET name = ?, country = ? "
                + "WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                   PreparedStatement updateManufacturerStatement
                           = connection.prepareStatement(updateQuery)) {
            updateManufacturerStatement.setString(PARAMETER_INDEX_1, manufacturer.getName());
            updateManufacturerStatement.setString(PARAMETER_INDEX_2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(PARAMETER_INDEX_3, manufacturer.getId());
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturers from DB "
                    + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteQuery = "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement
                        = connection.prepareStatement(deleteQuery)) {
            deleteManufacturerStatement.setLong(PARAMETER_INDEX_1, id);
            return deleteManufacturerStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturers from DB " + id, e);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        return new Manufacturer(id, name, country);
    }
}
