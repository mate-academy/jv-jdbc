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
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String createQuery = "INSERT INTO manufacturers(name, country) VALUES( ?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement = connection.prepareStatement(
                        createQuery, Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
            return manufacturer;
        } catch (SQLException exception) {
            throw new DataProcessingException("Can't create manufacturer "
                    + manufacturer.getName() + " in DB", exception);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        checkForNullId(id);
        String getQuery = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement = connection
                        .prepareStatement(getQuery)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getManufacturer(resultSet));
            }
            return Optional.empty();
        } catch (SQLException exception) {
            throw new DataProcessingException("Can't get manufacturer by id = "
                    + id + "from DB", exception);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllQuery = "SELECT * FROM manufacturers WHERE is_deleted = FALSE;";
        List<Manufacturer> manufacturerList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(getAllQuery);
            while (resultSet.next()) {
                manufacturerList.add(getManufacturer(resultSet));
            }
            return manufacturerList;
        } catch (SQLException exception) {
            throw new DataProcessingException("Can't get all manufacturers from DB", exception);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        checkForNullId(manufacturer.getId());
        String updateQuery = "UPDATE manufacturers SET name = ?, country = ? WHERE id = ? "
                + "AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement = connection
                        .prepareStatement(updateQuery, Statement.RETURN_GENERATED_KEYS)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException exception) {
            throw new DataProcessingException("Can't update manufacturer "
                    + manufacturer.getName() + " in DB", exception);
        }
    }

    @Override
    public boolean delete(Long id) {
        checkForNullId(id);
        String deleteQuery = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement = connection
                        .prepareStatement(deleteQuery)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() == 1;
        } catch (SQLException exception) {
            throw new DataProcessingException("Can't delete manufacturer with id = "
                    + id + " from DB", exception);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) {
        try {
            Manufacturer manufacturer = new Manufacturer(resultSet.getString("name"),
                    resultSet.getString("country"));
            manufacturer.setId(resultSet.getObject("id", Long.class));
            return manufacturer;
        } catch (SQLException exception) {
            throw new DataProcessingException("Can't create manufacturer", exception);
        }
    }

    private boolean checkForNullId(Long id) {
        if (id == null) {
            throw new RuntimeException("Manufacturer id can't be null for DB operation");
        }
        return true;
    }
}
