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
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String query = "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement createdManufacturerStatement = connection
                         .prepareStatement(query,
                         Statement.RETURN_GENERATED_KEYS)) {
            createdManufacturerStatement.setString(1, manufacturer.getName());
            createdManufacturerStatement.setString(2, manufacturer.getCountry());
            createdManufacturerStatement.executeUpdate();
            ResultSet generatedKey = createdManufacturerStatement.getGeneratedKeys();
            if (generatedKey.next()) {
                Long id = generatedKey.getObject(1, Long.class);
                manufacturer.setId(id);
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create manufactures in DB: "
                    + manufacturer, e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String query = "SELECT * FROM manufacturers "
                + "WHERE is_deleted = FALSE AND id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getManufacturerByIdStatement = connection
                        .prepareStatement(query)) {
            getManufacturerByIdStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerByIdStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getManufacturer(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturers from DB by id: " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String query = "SELECT * FROM manufacturers WHERE is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                 Statement getCarStatements = connection.createStatement()) {
            ResultSet resultSet = getCarStatements
                    .executeQuery(query);
            while (resultSet.next()) {
                allManufacturers.add(getManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("can't getAll manufacturers", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String query = "UPDATE manufacturers SET name = ?, "
                + "country = ? WHERE is_deleted = FALSE AND id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement updateManufacturerStatement
                        = connection.prepareStatement(query)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturers" + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE manufacturers SET is_deleted = TRUE "
                + "WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                  PreparedStatement deleteStatement = connection
                         .prepareStatement(query)) {
            deleteStatement.setLong(1, id);
            return deleteStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete some manufacturer from DB by id: "
                    + id, e);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) {
        Manufacturer manufacturer = new Manufacturer();
        try {
            manufacturer.setId(resultSet.getObject(1, Long.class));
            manufacturer.setName(resultSet.getString(2));
            manufacturer.setCountry(resultSet.getString(3));
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer " + resultSet, e);
        }
        return manufacturer;
    }
}
