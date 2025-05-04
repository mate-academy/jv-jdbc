package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import mate.jdbc.exceptions.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final int NAME_PARAM_INDEX = 1;
    private static final int COUNTRY_PARAM_INDEX = 2;
    private static final int ID_PARAM_INDEX = 3;
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String COUNTRY = "country";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest = "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createFormatStatement
                        = connection.prepareStatement(
                                insertManufacturerRequest, Statement.RETURN_GENERATED_KEYS);) {
            createFormatStatement.setString(
                    NAME_PARAM_INDEX, manufacturer.getName());
            createFormatStatement.setString(
                    COUNTRY_PARAM_INDEX, manufacturer.getCountry());
            createFormatStatement.executeUpdate();
            ResultSet generatedKeys = createFormatStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't insert manufacturer " + manufacturer.getName() + "to DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String selectManufacturerRequest =
                "SELECT * FROM manufacturers WHERE id = (?) AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getFormatStatement
                        = connection.prepareStatement(selectManufacturerRequest);) {
            getFormatStatement.setLong(1, id);
            ResultSet resultSet = getFormatStatement.executeQuery();
            Manufacturer manufacturer = null;
            while (resultSet.next()) {
                Manufacturer parsedManufacturer = parseManufacturer(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't get manufacturer with ID = " + id + " from DB", e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturerResultList = new ArrayList<>();
        String selectAllManufacturersRequest =
                "SELECT * FROM manufacturers WHERE is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllFormatsStatement = connection.createStatement();) {
            ResultSet resultSet =
                    getAllFormatsStatement.executeQuery(selectAllManufacturersRequest);
            while (resultSet.next()) {
                manufacturerResultList.add(parseManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return manufacturerResultList;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerRequest = "UPDATE manufacturers SET name = ?, country = ? "
                + "WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement
                        = connection.prepareStatement(updateManufacturerRequest);) {
            updateManufacturerStatement.setString(NAME_PARAM_INDEX, manufacturer.getName());
            updateManufacturerStatement.setString(COUNTRY_PARAM_INDEX, manufacturer.getCountry());
            updateManufacturerStatement.setLong(ID_PARAM_INDEX, manufacturer.getId());
            if (updateManufacturerStatement.executeUpdate() >= 1) {
                return manufacturer;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufactorer " + manufacturer, e);
        }
        throw new NoSuchElementException("Can't find this manufactorer " + manufacturer + " in DB");
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufacturerRequest = "UPDATE manufacturers SET is_deleted = "
                + "TRUE WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteFormatStatement =
                        connection.prepareStatement(deleteManufacturerRequest);) {
            deleteFormatStatement.setLong(1, id);
            return deleteFormatStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer with ID " + id, e);
        }
    }

    private Manufacturer parseManufacturer(ResultSet resultSet) throws SQLException {
        return new Manufacturer(
                resultSet.getObject("id", Long.class),
                resultSet.getNString("name"),
                resultSet.getNString("country"));
    }
}
