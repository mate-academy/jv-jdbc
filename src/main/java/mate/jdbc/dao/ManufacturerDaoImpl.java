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
import mate.jdbc.unit.ConnectionUnit;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final String INSERT
            = "INSERT INTO manufacturers (name, country) VALUES (?, ?)";
    private static final String GET_BY_ID
            = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = FALSE";
    private static final String GET_ALL
            = "SELECT * FROM manufacturers WHERE is_deleted = FALSE";
    private static final String UPDATE
            = "UPDATE manufacturers SET name = ?, country = ? WHERE id = ? AND is_deleted = FALSE";
    private static final String DELETE_BY_ID
            = "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ?";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUnit.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        INSERT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.executeUpdate();
            ResultSet keys = preparedStatement.getGeneratedKeys();
            while (keys.next()) {
                Long id = keys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create manufacturer " + manufacturer, e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        try (Connection connection = ConnectionUnit.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Optional<Manufacturer> result;
            if (resultSet.next()) {
                result = Optional.of(createManufacturer(resultSet));
            } else {
                result = Optional.empty();
            }
            return result;
        } catch (Exception e) {
            throw new DataProcessingException("Can't get manufacturer by id " + id,e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUnit.getConnection();
                PreparedStatement getAllManufacturersStatement
                        = connection.prepareStatement(GET_ALL)) {
            ResultSet resultSet = getAllManufacturersStatement.executeQuery();
            while (resultSet.next()) {
                manufacturers.add(createManufacturer(resultSet));
            }
            return manufacturers;
        } catch (Exception e) {
            throw new DataProcessingException("Can't get manufacturers", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUnit.getConnection();
                PreparedStatement preparedStatement
                        = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.setLong(3, manufacturer.getId());
            preparedStatement.executeUpdate();
            return manufacturer;
        } catch (Exception e) {
            throw new DataProcessingException("Can't update manufacturer " + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUnit.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            throw new DataProcessingException("Can't delete manufacturer by id " + id, e);
        }
    }

    private Manufacturer createManufacturer(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer("name", "country");
        manufacturer.setId(resultSet.getObject("id", Long.class));
        return manufacturer;
    }
}
