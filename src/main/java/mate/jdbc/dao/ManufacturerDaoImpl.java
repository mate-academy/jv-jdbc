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
        String insertManufacturerRequest =
                "INSERT INTO manufacturers(name, country, is_deleted) VALUES(?, ?, FALSE);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement
                        = connection.prepareStatement(insertManufacturerRequest,
                        Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t insert manufacturer to DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String query = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = FALSE;";
        Manufacturer manufacturer = null;
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getManufacturerById
                         = connection.prepareStatement(query)) {
            getManufacturerById.setLong(1, id);
            ResultSet manufacturerById = getManufacturerById.executeQuery();
            if (manufacturerById.next()) {
                manufacturer = getManufacturer(manufacturerById);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get manufacturer from DB by id " + id, e);
        }
        return Optional.ofNullable(manufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String query = "SELECT * FROM manufacturers WHERE is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getAllManufacturers = connection.prepareStatement(query)) {
            ResultSet resultSet = getAllManufacturers.executeQuery();
            while (resultSet.next()) {
                manufacturers.add(getManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all manufacturers from DB", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String insertManufacturerRequest =
                "UPDATE manufacturers SET name = ?, country = ? "
                        + "WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerById
                        = connection.prepareStatement(insertManufacturerRequest)) {
            getManufacturerById.setString(1, manufacturer.getName());
            getManufacturerById.setString(2, manufacturer.getCountry());
            getManufacturerById.setLong(3, manufacturer.getId());
            getManufacturerById.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update manufacturer in DB by id "
                    + manufacturer.getId(), e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String insertManufacturerRequest =
                "UPDATE manufacturers SET is_deleted = TRUE "
                        + "WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getManufacturerById
                         = connection.prepareStatement(insertManufacturerRequest)) {
            getManufacturerById.setLong(1, id);
            int updatedRows = getManufacturerById.executeUpdate();
            return updatedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete manufacturer in DB by id "
                    + id, e);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject(1, Long.class);
        String name = resultSet.getString(2);
        String country = resultSet.getString(3);
        return new Manufacturer(id, name, country);
    }
}
