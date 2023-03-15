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
        String insertManufacturerRequest = "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement
                        = connection.prepareStatement(insertManufacturerRequest,
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
            throw new DataProcessingException("Can't insert manufacturer to DB" + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getByIdManufacturerRequest = "SELECT * FROM manufacturers WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement
                        = connection.prepareStatement(getByIdManufacturerRequest)) {
            getManufacturerStatement.setLong(PARAMETER_INDEX_1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString(COLUMN_LABEL_NAME);
                String country = resultSet.getString(COLUMN_LABEL_COUNTRY);
                return Optional.of(new Manufacturer(id, name, country));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturers by id from DB" + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        String insertManufacturerRequest = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                   PreparedStatement getAllManufacturerStatement
                           = connection.prepareStatement(insertManufacturerRequest)) {
            ResultSet resultSet
                    = getAllManufacturerStatement.executeQuery(insertManufacturerRequest);
            while (resultSet.next()) {
                Long id = resultSet.getObject(COLUMN_LABEL_ID, Long.class);
                String name = resultSet.getString(COLUMN_LABEL_NAME);
                String country = resultSet.getString(COLUMN_LABEL_COUNTRY);
                Manufacturer manufacturer = new Manufacturer(id, name, country);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET name = ?, country = ? WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                   PreparedStatement updateManufacturerStatement
                           = connection.prepareStatement(updateRequest)) {
            updateManufacturerStatement.setString(PARAMETER_INDEX_1, manufacturer.getName());
            updateManufacturerStatement.setString(PARAMETER_INDEX_2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(PARAMETER_INDEX_3, manufacturer.getId());
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturers from DB"
                    + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement
                        = connection.prepareStatement(deleteRequest)) {
            deleteManufacturerStatement.setLong(PARAMETER_INDEX_1, id);
            return deleteManufacturerStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturers from DB" + id, e);
        }
    }
}
