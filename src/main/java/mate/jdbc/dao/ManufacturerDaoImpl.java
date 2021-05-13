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
import mate.jdbc.util.ConnectionUtilImpl;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String createManufacturerRequest
                = "INSERT INTO manufacturers(name, country) VALUES(?, ?);";
        try (Connection connection = ConnectionUtilImpl.getConnection();
                PreparedStatement createManufacturerStatement
                        = connection.prepareStatement(createManufacturerRequest,
                        Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            while (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create request for input params. "
                    + "Manufacturer: name = "
                    + manufacturer.getName()
                    + ", country = " + manufacturer.getCountry(), e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturerRequest =
                "SELECT * FROM manufacturers WHERE id = ?";
        try (Connection connection = ConnectionUtilImpl.getConnection();
                PreparedStatement getManufacturerStatement
                        = connection.prepareStatement(getManufacturerRequest)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            if (resultSet.next() && !resultSet.getBoolean("is_deleted")) {
                return Optional.of(createManufacturerObject(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create request for find manufacturer", e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String getAllManufacturersRequest =
                "SELECT * FROM manufacturers";

        try (Connection connection = ConnectionUtilImpl.getConnection();
                Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(getAllManufacturersRequest);
            while (resultSet.next() && !resultSet.getBoolean("is_deleted")) {
                Manufacturer newManufacturer = createManufacturerObject(resultSet);
                manufacturers.add(newManufacturer);
            }
            return manufacturers;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create request for return all DB", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerRequest =
                "UPDATE manufacturers SET name = ?, country = ? WHERE id = ?";
        try (Connection connection = ConnectionUtilImpl.getConnection();
                PreparedStatement updateStatement =
                        connection.prepareStatement(updateManufacturerRequest)) {
            if (get(manufacturer.getId()).isPresent()) {
                updateStatement.setString(1, manufacturer.getName());
                updateStatement.setString(2, manufacturer.getCountry());
                updateStatement.setLong(3, manufacturer.getId());
                updateStatement.executeUpdate();
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create update request for input data. "
                    + "Manufacturer: id = "
                    + manufacturer.getId()
                    + ", name = "
                    + manufacturer.getName()
                    + ", country = "
                    + manufacturer.getCountry(), e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufacturerByIdRequest
                = "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ?";

        try (Connection connection = ConnectionUtilImpl.getConnection();
                PreparedStatement deleteManufacturerStatement
                        = connection.prepareStatement(deleteManufacturerByIdRequest,
                        Statement.RETURN_GENERATED_KEYS)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't create delete request for input params. Manufacturer id = " + id, e);
        }
    }

    private Manufacturer createManufacturerObject(ResultSet resultSet) {
        try {
            Long id = resultSet.getObject("id", Long.class);
            String name = resultSet.getString("name");
            String country = resultSet.getString("country");
            Manufacturer newManufacturer = new Manufacturer();
            newManufacturer.setId(id);
            newManufacturer.setName(name);
            newManufacturer.setCountry(country);
            return newManufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get data about manufacturer", e);
        }
    }
}
