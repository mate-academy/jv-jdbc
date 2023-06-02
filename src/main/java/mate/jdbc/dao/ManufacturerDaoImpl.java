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
    public static final int NAME_PARAMETER_INDEX = 1;
    public static final int COUNTRY_PARAMETER_INDEX = 2;
    public static final int ID_PARAMETER_INDEX = 3;
    public static final int COLUMN_INDEX = 1;
    public static final int PARAMETER_INDEX = 1;
    private Manufacturer manufacturer;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest = "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement =
                        connection.prepareStatement(
                             insertManufacturerRequest, Statement.RETURN_GENERATED_KEYS)) {

            createManufacturerStatement.setString(NAME_PARAMETER_INDEX, manufacturer.getName());
            createManufacturerStatement.setString(
                    COUNTRY_PARAMETER_INDEX, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(COLUMN_INDEX, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t insert manufacturer to DB" + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getByIdRequest = "SELECT * FROM manufacturers "
                + "WHERE is_deleted = 'FALSE' AND id = ?;";
        Manufacturer manufacturerFromResultSet = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getByIdStatement =
                        connection.prepareStatement(getByIdRequest)) {

            getByIdStatement.setLong(PARAMETER_INDEX, id);
            ResultSet resultSet = getByIdStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getObject("id", Long.class).equals(id)) {
                    manufacturerFromResultSet =
                            getManufacturerFromResultSet(resultSet, manufacturer);
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can`t get manufacturer by id from DB. id = " + id, e);
        }
        return Optional.ofNullable(manufacturerFromResultSet);
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturersStatement =
                        connection.prepareStatement("SELECT * FROM manufacturers "
                             + "WHERE is_deleted = 'FALSE';")) {
            ResultSet resultSet = getAllManufacturersStatement.executeQuery();
            while (resultSet.next()) {
                Manufacturer manufacturerFromResultSet =
                        getManufacturerFromResultSet(resultSet, manufacturer);
                manufacturers.add(manufacturerFromResultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can`t get all manufacturers from DB" + manufacturers, e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest =
                "UPDATE manufacturers SET name = ?, country = ? "
                        + "WHERE is_deleted = 'FALSE' AND id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement =
                        connection.prepareStatement(updateRequest)) {

            updateManufacturerStatement.setString(NAME_PARAMETER_INDEX, manufacturer.getName());
            updateManufacturerStatement.setString(
                    COUNTRY_PARAMETER_INDEX, manufacturer.getCountry());
            updateManufacturerStatement.setLong(ID_PARAMETER_INDEX, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update manufacturer: " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = 'TRUE' "
                + "WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement =
                        connection.prepareStatement(deleteRequest)) {
            deleteManufacturerStatement.setLong(PARAMETER_INDEX, id);
            return deleteManufacturerStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can`t delete manufacturer by id from DB. id = " + id, e);
        }
    }

    private Manufacturer getManufacturerFromResultSet(
            ResultSet resultSet, Manufacturer manufacturer) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        manufacturer.setId(id);
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        return manufacturer;
    }
}
