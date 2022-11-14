package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.exception.DataProcessException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturersDaoImpl implements ManufacturersDao {
    private static final int FIRST_PARAMETER = 1;
    private static final int SECOND_PARAMETER = 2;
    private static final int THIRD_PARAMETER = 3;

    public Manufacturer create(Manufacturer manufacturer) {
        String query = "INSERT INTO manufacturers(name,country) VALUES(?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(FIRST_PARAMETER, manufacturer.getName());
            statement.setString(SECOND_PARAMETER, manufacturer.getCountry());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(FIRST_PARAMETER, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessException("Can't insert row to manufacturers with manufacturer id"
                    + manufacturer.getId(), e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String query = "SELECT id, name, country FROM manufacturers "
                + "WHERE id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(FIRST_PARAMETER, id);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next()
                    ? Optional.of(parseManufacturer(resultSet))
                    : Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessException("Cant get manufacturer by id " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String query = "SELECT * FROM manufacturers WHERE is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery(query);
            List<Manufacturer> manufacturers = new ArrayList<>();
            while (resultSet.next()) {
                manufacturers.add(parseManufacturer(resultSet));
            }
            return manufacturers;
        } catch (SQLException e) {
            throw new DataProcessException("Cant get all manufacturers from DB", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String query = "UPDATE  manufacturers "
                + "SET name = ?, country = ? "
                + "WHERE id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(FIRST_PARAMETER, manufacturer.getName());
            statement.setString(SECOND_PARAMETER, manufacturer.getCountry());
            statement.setLong(THIRD_PARAMETER, manufacturer.getId());
            statement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessException("Cant update manufacturer with id "
                    + manufacturer.getId(), e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(FIRST_PARAMETER,id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessException("Cant delete manufacturer with id" + id, e);
        }
    }

    private Manufacturer parseManufacturer(ResultSet resultSet) {
        try {
            String name = resultSet.getString("name");
            String country = resultSet.getString("country");
            Long id = resultSet.getObject(FIRST_PARAMETER, Long.class);
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setId(id);
            manufacturer.setCountry(country);
            manufacturer.setName(name);
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessException("Cant create manufacturer", e);
        }
    }
}
