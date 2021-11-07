package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.lib.ConnectionUtil;
import mate.jdbc.lib.Dao;
import mate.jdbc.lib.DataProcessingException;
import mate.jdbc.model.Manufacturer;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManRequest = "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement insertManStatement
                         = connection.prepareStatement(insertManRequest,
                        Statement.RETURN_GENERATED_KEYS)) {
            insertManStatement.setString(1, manufacturer.getName());
            insertManStatement.setString(2, manufacturer.getCountry());
            insertManStatement.executeUpdate();
            ResultSet generatedKeys = insertManStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer to DB: "
                    + manufacturer, e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManRequest = "SELECT * FROM manufacturers WHERE is_deleted = false AND id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getManStatement
                         = connection.prepareStatement(getManRequest)) {
            getManStatement.setLong(1, id);
            ResultSet resultSet = getManStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getManFromResultSet(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer from DB by id: " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllManRequest = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getAllManStatement
                         = connection.prepareStatement(getAllManRequest)) {
            ResultSet resultSet = getAllManStatement.executeQuery();
            List<Manufacturer> manufacturersList = new ArrayList<>();
            while (resultSet.next()) {
                manufacturersList.add(getManFromResultSet(resultSet));
            }
            return manufacturersList;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManRequest = "UPDATE manufacturers SET name = ?, "
                + "country = ? WHERE is_deleted = false AND id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement updateManStatement
                         = connection.prepareStatement(updateManRequest)) {
            updateManStatement.setString(1, manufacturer.getName());
            updateManStatement.setString(2, manufacturer.getCountry());
            updateManStatement.setLong(3, manufacturer.getId());
            if (updateManStatement.executeUpdate() > 0) {
                return manufacturer;
            } else {
                throw new DataProcessingException("Can't update manufacturer in DB: "
                        + manufacturer, new SQLException("id was not found or deleted"));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer in DB: "
                    + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteManRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement deleteManStatement
                         = connection.prepareStatement(deleteManRequest)) {
            deleteManStatement.setLong(1, id);
            return deleteManStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer from DB by id: " + id, e);
        }
    }

    private Manufacturer getManFromResultSet(ResultSet resultSet) {
        Manufacturer manufacturer = new Manufacturer();
        try {
            manufacturer.setId(resultSet.getObject("id", Long.class));
            manufacturer.setName(resultSet.getString("name"));
            manufacturer.setCountry(resultSet.getString("country"));
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get information from ResultSet: "
                    + resultSet, new SQLException());
        }
    }
}
