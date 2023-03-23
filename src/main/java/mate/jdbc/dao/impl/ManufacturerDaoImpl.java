package mate.jdbc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final String ID_COLUMN_LABEL = "id";
    private static final String NAME_COLUMN_LABEL = "name";
    private static final String COUNTRY_COLUMN_LABEL = "country";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertRequest = "INSERT INTO manufacturers (name, country) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createStatement =
                        connection.prepareStatement(insertRequest,
                                Statement.RETURN_GENERATED_KEYS)) {
            createStatement.setString(1, manufacturer.getName());
            createStatement.setString(2, manufacturer.getCountry());
            createStatement.executeUpdate();
            ResultSet generatedKeys = createStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                manufacturer.setId(generatedKeys.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add manufacturer to table.", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(long id) {
        String getByIdRequest = "SELECT * FROM manufacturers "
                + "WHERE id = ? AND is_deleted IS NOT TRUE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getByIdStatement = connection.prepareStatement(getByIdRequest)) {
            getByIdStatement.setLong(1, id);
            ResultSet resultSet = getByIdStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer from table.", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllRequest = "SELECT * FROM manufacturers WHERE is_deleted IS NOT TRUE";
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getAllStatement = connection.prepareStatement(getAllRequest)) {
            ResultSet resultSet = getAllStatement.executeQuery();
            while (resultSet.next()) {
                manufacturers.add(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from table.", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest
                = "UPDATE manufacturers SET name = ?, country = ?, is_deleted = FALSE WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement updateStatement = connection.prepareStatement(updateRequest)) {
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.setLong(3, manufacturer.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer.", e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement deleteByIdStatement = connection
                         .prepareStatement(deleteRequest)) {
            deleteByIdStatement.setLong(1, id);
            return deleteByIdStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer from table.", e);
        }
    }

    private Manufacturer parseResultSet(ResultSet resultSet) {
        Manufacturer manufacturer = new Manufacturer();
        try {
            manufacturer.setId(resultSet.getObject(ID_COLUMN_LABEL, Long.class));
            manufacturer.setName(resultSet.getString(NAME_COLUMN_LABEL));
            manufacturer.setCountry(resultSet.getString(COUNTRY_COLUMN_LABEL));
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer from ResultSet.", e);
        }
        return manufacturer;
    }
}
