package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.exeption.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final int PARAMETER_INDEX_NAME = 1;
    private static final int PARAMETER_INDEX_COUNTRY = 2;
    private static final int PARAMETER_INDEX_ID = 3;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertFormatRequest = "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createFormatStatement = connection
                        .prepareStatement(insertFormatRequest, Statement.RETURN_GENERATED_KEYS)) {
            createFormatStatement.setString(PARAMETER_INDEX_NAME, manufacturer.getName());
            createFormatStatement.setString(PARAMETER_INDEX_COUNTRY, manufacturer.getCountry());
            createFormatStatement.executeUpdate();
            ResultSet generatedKeys = createFormatStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(PARAMETER_INDEX_NAME, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer to DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(long id) {
        String getManufacturerRequest = "SELECT * FROM manufacturers WHERE id = ? "
                + "AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerPrepared =
                        connection.prepareStatement(getManufacturerRequest)) {
            Manufacturer manufacturer = new Manufacturer();
            getManufacturerPrepared.setLong(PARAMETER_INDEX_NAME,id);
            ResultSet resultSet = getManufacturerPrepared.executeQuery();
            if (resultSet.next()) {
                manufacturer = getManufacturer(resultSet);
            }
            return Optional.of(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer info from DB", e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String getManufacturerRequest = "SELECT * FROM manufacturers WHERE is_deleted = FALSE";
        List<Manufacturer> allManufacturerInfo = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAll = connection.prepareStatement(getManufacturerRequest)) {
            ResultSet resultSet = getAll.executeQuery(getManufacturerRequest);
            while (resultSet.next()) {
                allManufacturerInfo.add(getManufacturer(resultSet));
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturer info from DB", e);
        }
        return allManufacturerInfo;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String update = "UPDATE manufacturers SET name = ?, country = ? "
                + "WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement = connection.prepareStatement(update)) {
            updateStatement.setString(PARAMETER_INDEX_NAME,manufacturer.getName());
            updateStatement.setString(PARAMETER_INDEX_COUNTRY,manufacturer.getCountry());
            updateStatement.setLong(PARAMETER_INDEX_ID,manufacturer.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update info from DB", e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true where id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement =
                        connection.prepareStatement(deleteRequest,
                             Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setLong(PARAMETER_INDEX_NAME,id);
            return createManufacturerStatement.executeUpdate() >= PARAMETER_INDEX_NAME;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete format from DB", e);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) {
        try {
            Manufacturer manufacturer = new Manufacturer();
            Long id = resultSet.getObject("id", Long.class);
            String name = resultSet.getString("name");
            String country = resultSet.getString("country");
            manufacturer.setId(id);
            manufacturer.setName(name);
            manufacturer.setCountry(country);
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer", e);
        }
    }
}
