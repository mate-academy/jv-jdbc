package mate.jdbc.db;

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
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final String TABLE = "manufacturers";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_COUNTRY = "country";
    private static final String COLUMN_IS_DELETED = "is_deleted";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest = "INSERT INTO "
                + TABLE + "(" + COLUMN_NAME + ", " + COLUMN_COUNTRY + ") VALUES(?,?);";
        int nameSubstitutionQueue = 1;
        int countrySubstitutionQueue = 2;

        try (Connection connection = ConnectionUtil.getDbConnection();
                PreparedStatement createManufactureStatement = connection.prepareStatement(
                            insertManufacturerRequest, Statement.RETURN_GENERATED_KEYS)) {
            createManufactureStatement.setString(nameSubstitutionQueue, manufacturer.getName());
            createManufactureStatement
                    .setString(countrySubstitutionQueue, manufacturer.getCountry());
            createManufactureStatement.executeUpdate();
            ResultSet generatedKey = createManufactureStatement.getGeneratedKeys();
            if (generatedKey.next()) {
                Long id = generatedKey.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer to DB." + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufactureRequest = "SELECT * FROM "
                + TABLE + " WHERE id = ? AND " + COLUMN_IS_DELETED + " = false;";

        try (Connection connection = ConnectionUtil.getDbConnection();
                PreparedStatement getManufacturerStatement =
                        connection.prepareStatement(getManufactureRequest)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet manufacturerResult = getManufacturerStatement.executeQuery();
            if (manufacturerResult.next()) {
                return Optional.of(getManufacturer(manufacturerResult));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get a manufacturer by id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String getAllManufacturersRequest = "SELECT * FROM "
                + TABLE + " WHERE " + COLUMN_IS_DELETED + " = false;";

        try (Connection connection = ConnectionUtil.getDbConnection();
                Statement getAllManufacturersStatement = connection.createStatement()) {
            ResultSet resultSet =
                    getAllManufacturersStatement.executeQuery(getAllManufacturersRequest);
            while (resultSet.next()) {
                manufacturers.add(getManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers.", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerRequest = "UPDATE "
                + TABLE + " SET " + COLUMN_NAME + " = ?, " + COLUMN_COUNTRY + " = ? WHERE id = ?;";
        int nameSubstitutionQueue = 1;
        int countrySubstitutionQueue = 2;
        int idSubstitutionQueue = 3;

        try (Connection connection = ConnectionUtil.getDbConnection();
                PreparedStatement updateManufactureStatement = connection.prepareStatement(
                        updateManufacturerRequest, Statement.RETURN_GENERATED_KEYS)) {
            updateManufactureStatement.setString(nameSubstitutionQueue, manufacturer.getName());
            updateManufactureStatement
                    .setString(countrySubstitutionQueue, manufacturer.getCountry());
            updateManufactureStatement.setLong(idSubstitutionQueue, manufacturer.getId());
            updateManufactureStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer with id = "
                    + manufacturer.getId(), e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufacturerRequest = "UPDATE "
                + TABLE + " SET " + COLUMN_IS_DELETED + " = true WHERE id = ?;";

        try (Connection connection = ConnectionUtil.getDbConnection();
                PreparedStatement deleteManufactureStatement = connection.prepareStatement(
                        deleteManufacturerRequest, Statement.RETURN_GENERATED_KEYS)) {
            deleteManufactureStatement.setLong(1, id);
            deleteManufactureStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    private static Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getObject(1, Long.class));
        manufacturer.setCountry(resultSet.getString("country"));
        manufacturer.setName(resultSet.getString("name"));
        return manufacturer;
    }
}
