package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.exceptions.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest =
                "INSERT INTO manufacturers (name, country) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement =
                        connection.prepareStatement(insertManufacturerRequest,
                        Statement.RETURN_GENERATED_KEYS)) {
            extractManufacturerFromResultSet(manufacturer, createManufacturerStatement);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer to DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturerRequest =
                "SELECT * FROM manufacturers WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement =
                        connection.prepareStatement(getManufacturerRequest,
                        Statement.NO_GENERATED_KEYS)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet manufacturerResultSet = getResultSet(getManufacturerStatement);
            List<Manufacturer> manufacturersList =
                    convertResultSetToManufacturersList(manufacturerResultSet);
            return findManufacturerOnId(id, manufacturersList);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find manufacturer with id " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllManufacturersRequest =
                "SELECT * FROM manufacturers WHERE is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturersStatement =
                        connection.prepareStatement(getAllManufacturersRequest,
                             Statement.NO_GENERATED_KEYS)) {
            ResultSet manufacturerResultSet = getResultSet(getAllManufacturersStatement);
            return convertResultSetToManufacturersList(manufacturerResultSet);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest =
                "UPDATE manufacturers SET name = ?, country = ? WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement =
                        connection.prepareStatement(updateRequest, Statement.NO_GENERATED_KEYS)) {
            get(manufacturer.getId());
            setUpdatedParams(manufacturer, updateManufacturerStatement);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update value from id "
                    + manufacturer.getId(), e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest =
                "UPDATE manufacturers SET is_deleted = true where id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement =
                        connection.prepareStatement(deleteRequest, Statement.NO_GENERATED_KEYS)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer from DB", e);
        }
    }

    private void extractManufacturerFromResultSet(Manufacturer manufacturer,
                                                  PreparedStatement createManufacturerStatement)
            throws SQLException {
        createManufacturerStatement.setString(1, manufacturer.getName());
        createManufacturerStatement.setString(2, manufacturer.getCountry());
        createManufacturerStatement.executeUpdate();
        ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            Long id = generatedKeys.getObject(1, Long.class);
            manufacturer.setId(id);
        }
    }

    private ResultSet getResultSet(PreparedStatement preparedStatement) throws SQLException {
        return preparedStatement.executeQuery();
    }

    private Optional<Manufacturer> findManufacturerOnId(Long id,
                                                        List<Manufacturer> manufacturers) {
        return Optional.ofNullable(manufacturers.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Can't find manufacturer from id " + id)));
    }

    private void setUpdatedParams(Manufacturer manufacturer,
                                  PreparedStatement updateManufacturerStatement)
            throws SQLException {
        updateManufacturerStatement.setString(1, manufacturer.getName());
        updateManufacturerStatement.setString(2, manufacturer.getCountry());
        updateManufacturerStatement.setLong(3, manufacturer.getId());
        updateManufacturerStatement.executeUpdate();
    }

    private List<Manufacturer> convertResultSetToManufacturersList(ResultSet resultSet)
            throws SQLException {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        if (resultSet == null) {
            throw new RuntimeException("DB is empty");
        }
        while (resultSet.next()) {
            Long id = resultSet.getObject("id", Long.class);
            String name = resultSet.getString("name");
            String country = resultSet.getString("country");
            Manufacturer manufacturer = new Manufacturer(id, name, country);
            allManufacturers.add(manufacturer);
        }
        return allManufacturers;
    }
}


