package mate.jdbc.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.lib.DaoAnnotation;
import mate.jdbc.util.ConnectionUtil;

@DaoAnnotation
public class ManufacturerDao implements Dao<Manufacturer> {
    private Manufacturer manufacturer = new Manufacturer();

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertQuery = "INSERT INTO manufacturers(name, country) "
                + "VALUES(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement insert = connection.prepareStatement(
                        insertQuery,
                        Statement.RETURN_GENERATED_KEYS);) {
            insert.setString(1, manufacturer.getName());
            insert.setString(2, manufacturer.getCountry());
            insert.executeUpdate();
            ResultSet resultSet = insert.getGeneratedKeys();
            if (resultSet.next()) {
                manufacturer.setId(resultSet.getLong(1));
            }
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't insert manufacturer to db");
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Statement get;
        try (Connection connection = ConnectionUtil.getConnection();) {
            get = connection.createStatement();
            ResultSet resultSet = get.executeQuery(String.format(
                    "SELECT * FROM manufacturers WHERE id = %d;", id.longValue()));
            if (resultSet.next()) {
                if (resultSet.getBoolean(4)) {
                    this.manufacturer = getManufacturer(resultSet.getLong(1),
                            resultSet.getString("name"),
                            resultSet.getString("country"));
                } else {
                    get.close();
                    return Optional.empty();
                }
            }
            get.close();
            return Optional.of(manufacturer);
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't get all formats from db");
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        Statement getAll;
        List<Manufacturer> result = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();) {
            getAll = connection.createStatement();
            ResultSet resultSet = getAll.executeQuery(
                    "SELECT * FROM manufacturers;");
            while (resultSet.next()) {
                if (resultSet.getBoolean(4)) {
                    result.add(new Manufacturer(
                            resultSet.getLong(1),
                            resultSet.getString("name"),
                            resultSet.getString("country")
                    ));
                }
            }
            getAll.close();
            return result;
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't get all formats from db");
        }
    }

    @Override
    public Optional<Manufacturer> update(Manufacturer manufacturer) {
        String updateQuery = "UPDATE manufacturers SET name = ?,"
                + " country = ? WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement =
                            connection.prepareStatement(updateQuery,
                                Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.setLong(3, manufacturer.getId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                this.manufacturer = getManufacturer(manufacturer.getId(),
                        resultSet.getString("name"),
                        resultSet.getString("country"));
            }
            return Optional.of(this.manufacturer);
        } catch (SQLException throwables) {
            throw new RuntimeException(
                    "Can't update element here: " + manufacturer.getId() + " index");
        }
    }

    @Override
    public boolean delete(Long columnId) {
        String updateQuary = "UPDATE manufacturers"
                + " SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(updateQuary,
                             Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setLong(1, columnId);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getBoolean("is_deleted");
            }
            return true;
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't delete manufacturer at this index");
        }
    }

    private Manufacturer getManufacturer(Long id, String name, String country) {
        return new Manufacturer(manufacturer.getId(),
                name,
                country);
    }
}
