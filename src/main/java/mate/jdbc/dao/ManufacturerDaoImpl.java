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
    private static final String ID_COLUMN_LABEL = "id";
    private static final String NAME_COLUMN_LABEL = "name";
    private static final String COUNTRY_COLUMN_LABEL = "country";
    private static final int FIRST_PARAMETER_INDEX = 1;
    private static final int SECOND_PARAMETER_INDEX = 2;
    private static final int THIRD_PARAMETER_INDEX = 3;
    private static final int ID_COLUMN_INDEX = 1;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest = "INSERT INTO manufacturers(name, country) VALUES(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement = connection
                        .prepareStatement(insertManufacturerRequest,
                                Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(FIRST_PARAMETER_INDEX, manufacturer.getName());
            createManufacturerStatement.setString(SECOND_PARAMETER_INDEX,
                    manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(ID_COLUMN_INDEX, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer "
                    + manufacturer + " to DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturerRequest =
                "SELECT * FROM manufacturers WHERE is_deleted = false AND id = ?;";
        Manufacturer getManufacturer = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement = connection
                        .prepareStatement(getManufacturerRequest)) {
            getManufacturerStatement.setLong(FIRST_PARAMETER_INDEX, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString(NAME_COLUMN_LABEL);
                String country = resultSet.getString(COUNTRY_COLUMN_LABEL);
                getManufacturer = new Manufacturer(id, name, country);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer from DB by id " + id, e);
        }
        return Optional.ofNullable(getManufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String getAllManufacturersRequest =
                "SELECT * FROM manufacturers WHERE is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturersStatement =
                        connection.prepareStatement(getAllManufacturersRequest)) {
            ResultSet resultSet = getAllManufacturersStatement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getObject(ID_COLUMN_LABEL, Long.class);
                String name = resultSet.getString(NAME_COLUMN_LABEL);
                String country = resultSet.getString(COUNTRY_COLUMN_LABEL);
                manufacturers.add(new Manufacturer(id, name, country));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
        return manufacturers;
    }

    @Override
    public Optional<Manufacturer> update(Manufacturer manufacturer) {
        String updateManufacturerRequest =
                "UPDATE manufacturers SET name = ?,  country = ? "
                        + "WHERE is_deleted = false AND id = ?;";
        Optional<Manufacturer> optionalManufacturer = get(manufacturer.getId());
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement = connection
                        .prepareStatement(updateManufacturerRequest)) {
            updateManufacturerStatement.setString(FIRST_PARAMETER_INDEX,
                    manufacturer.getName());
            updateManufacturerStatement.setString(SECOND_PARAMETER_INDEX,
                    manufacturer.getCountry());
            updateManufacturerStatement.setLong(THIRD_PARAMETER_INDEX,
                    manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer "
                    + manufacturer + " in DB", e);
        }
        return optionalManufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufacturerRequest =
                "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement = connection
                        .prepareStatement(deleteManufacturerRequest)) {
            deleteManufacturerStatement.setLong(FIRST_PARAMETER_INDEX, id);
            return deleteManufacturerStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update 'is_deleted' of manufacturer with id "
                    + id + " in DB", e);
        }
    }
}
