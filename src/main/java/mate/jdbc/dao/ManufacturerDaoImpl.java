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
    private static final String INSERT_MANUFACTURER_REQUEST =
            "INSERT INTO manufacturers (name, country) VALUES (?, ?);";
    private static final String GET_MANUFACTURER_REQUEST =
            "SELECT * FROM manufacturers WHERE id = ?;";
    private static final String GET_ALL_MANUFACTURERS_REQUEST =
            "SELECT * FROM manufacturers WHERE is_deleted = FALSE";
    private static final String UPDATE_REQUEST =
            "UPDATE manufacturers SET name = ?, country = ? WHERE id = ?;";
    private static final String DELETE_REQUEST =
            "UPDATE manufacturers SET is_deleted = true where id = ?;";
    private static final Connection CONNECTION = ConnectionUtil.getConnection();

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try (PreparedStatement createManufacturerStatement =
                CONNECTION.prepareStatement(INSERT_MANUFACTURER_REQUEST,
                        Statement.RETURN_GENERATED_KEYS)) {
            createNewManufacturer(manufacturer, createManufacturerStatement);
            createKey(manufacturer, createManufacturerStatement);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer to DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        try (PreparedStatement getManufacturerStatement =
                CONNECTION.prepareStatement(GET_MANUFACTURER_REQUEST,
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
        try (PreparedStatement getAllManufacturersStatement =
                     CONNECTION.prepareStatement(GET_ALL_MANUFACTURERS_REQUEST,
                             Statement.NO_GENERATED_KEYS)) {
            ResultSet manufacturerResultSet = getResultSet(getAllManufacturersStatement);
            return convertResultSetToManufacturersList(manufacturerResultSet);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        try (PreparedStatement updateManufacturerStatement =
                CONNECTION.prepareStatement(UPDATE_REQUEST, Statement.NO_GENERATED_KEYS)) {
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
        try (PreparedStatement deleteManufacturerStatement =
                     CONNECTION.prepareStatement(DELETE_REQUEST, Statement.NO_GENERATED_KEYS)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer from DB", e);
        }
    }

    private void createNewManufacturer(Manufacturer manufacturer,
                                       PreparedStatement createManufacturerStatement)
            throws SQLException {
        createManufacturerStatement.setString(1, manufacturer.getName());
        createManufacturerStatement.setString(2, manufacturer.getCountry());
        createManufacturerStatement.executeUpdate();
    }

    private void createKey(Manufacturer manufacturer, PreparedStatement createManufacturerStatement)
            throws SQLException {
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


