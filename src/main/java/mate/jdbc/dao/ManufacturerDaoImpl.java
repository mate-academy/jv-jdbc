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

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String createManufacturerRequest =
                "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement = connection.prepareStatement(
                        createManufacturerRequest, Statement.RETURN_GENERATED_KEYS)) {
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
        String getManufacturerRequest =
                "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement = connection
                        .prepareStatement(getManufacturerRequest)) {
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
        String getAllManufacturerRequest =
                "SELECT * FROM manufacturers WHERE is_deleted = FALSE;";
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturersStatement =
                        connection.prepareStatement(getAllManufacturerRequest)) {
            ResultSet resultSet = getAllManufacturersStatement
                       .executeQuery(getAllManufacturerRequest);
            while (resultSet.next()) {
                allManufacturers.add(createManufacturer(resultSet));
            }
        } catch (Exception ex) {
            throw new DataProcessingException("Can't get all manufacturers from DB", ex);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerRequest = "UPDATE manufacturers SET name = ?, "
                + "country = ? WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement = connection
                        .prepareStatement(updateManufacturerRequest)) {
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
        String deleteManufacturerRequest =
                "UPDATE manufacturers SET is_deleted = TRUE where id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement = connection
                        .prepareStatement(deleteManufacturerRequest)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() > 0;
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
