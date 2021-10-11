package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import mate.jdbc.lib.Dao;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest = "INSERT INTO manufacturers (name, country) values(?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement = connection.prepareStatement(
                        insertManufacturerRequest,
                        Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKey = createManufacturerStatement.getGeneratedKeys();
            if (generatedKey.next()) {
                Long id = generatedKey.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not insert manufacturer to DB!!!", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String
                getManufacturerRequest
                = "SELECT * FROM manufacturers WHERE is_deleted = FALSE && id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement = connection.prepareStatement(
                        getManufacturerRequest)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(parseResultSet(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllManufacturersStatement = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturersStatement.executeQuery(
                    "SELECT * FROM manufacturers WHERE is_deleted = FALSE ;");
            while (resultSet.next()) {
                manufacturers.add(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get manufacturers from DB!!!", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String
                updateManufacturerRequest
                = "UPDATE manufacturers SET name = ?, country = ? WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement = connection.prepareStatement(
                        updateManufacturerRequest)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
            return get(manufacturer.getId()).get();
        } catch (NoSuchElementException e) {
            throw new DataProcessingException("This manufacturer not found in DB ", e);
        } catch (SQLException e) {
            throw new DataProcessingException("Can not update manufacturer in DB", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String
                deleteManufacturerRequest
                = "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement = connection.prepareStatement(
                        deleteManufacturerRequest)) {
            if (!new ManufacturerDaoImpl().get(id).isPresent()) {
                return false;
            }
            deleteManufacturerStatement.setLong(1, id);
            deleteManufacturerStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    private Manufacturer parseResultSet(ResultSet resultSet) {
        try {
            Long id = resultSet.getLong("id");
            String manufacturer = resultSet.getString("name");
            String country = resultSet.getString("country");
            Manufacturer manufacturerFromDb = new Manufacturer();
            manufacturerFromDb.setId(id);
            manufacturerFromDb.setName(manufacturer);
            manufacturerFromDb.setCountry(country);
            return manufacturerFromDb;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get manufacturers from DB!!!", e);
        }
    }
}
