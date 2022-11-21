package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    private static final String CREATE_MANUFACTURER_REQUEST =
            "INSERT INTO manufacturers(name, country) values(?, ?);";
    private static final String GET_MANUFACTURER_REQUEST =
            "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = FALSE;";
    private static final String GET_ALL_MANUFACTURER_REQUEST =
            "SELECT * FROM manufacturers WHERE is_deleted = FALSE;";
    private static final String UPDATE_MANUFACTURER_REQUEST =
            "UPDATE manufacturers SET name = ?, country = ? WHERE id = ? AND is_deleted = FALSE;";
    private static final String DELETE_MANUFACTURER_REQUEST =
            "UPDATE manufacturers SET is_deleted = TRUE where id = ?;";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement = connection.prepareStatement(
                        CREATE_MANUFACTURER_REQUEST, Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (Exception ex) {
            throw new DataProcessingException("Can't create manufacturer: "
                    + manufacturer + " in DB", ex);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement = connection
                        .prepareStatement(GET_MANUFACTURER_REQUEST)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(createManufacturer(resultSet));
            }
            return Optional.empty();
        } catch (Exception ex) {
            throw new DataProcessingException("Can't find manufacturer by id: "
                    + id + " in DB", ex);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllManufacturersStatement = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturersStatement
                       .executeQuery(GET_ALL_MANUFACTURER_REQUEST);
            while (resultSet.next()) {
                Long id = resultSet.getObject("id", Long.class);
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(id);
                manufacturer.setName(name);
                manufacturer.setCountry(country);
                allManufacturers.add(manufacturer);
            }
        } catch (Exception ex) {
            throw new DataProcessingException("Can't get all manufacturers from DB", ex);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement = connection
                        .prepareStatement(UPDATE_MANUFACTURER_REQUEST)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
            return manufacturer;
        } catch (Exception ex) {
            throw new DataProcessingException("Can't update manufacturer: "
                    + manufacturer + " in DB", ex);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement = connection
                        .prepareStatement(DELETE_MANUFACTURER_REQUEST)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() >= 1;
        } catch (Exception e) {
            throw new DataProcessingException("Can't delete manufacturer by id: "
                    + id + " in DB", e);
        }
    }

    private Manufacturer createManufacturer(ResultSet resultSet) {
        try {
            return new Manufacturer(
                    resultSet.getObject("id", Long.class),
                    resultSet.getString("name"),
                    resultSet.getString("country"));
        } catch (Exception ex) {
            throw new DataProcessingException("Can't create manufacturer from ResultSet: "
                    + resultSet, ex);
        }
    }
}
