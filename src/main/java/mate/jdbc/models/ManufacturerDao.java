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
    private Connection connection = ConnectionUtil.getConnection();
    private Manufacturer manufacturer = new Manufacturer();

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try {
            String insertQuery = "INSERT INTO manufacturers(name, country) "
                    + "VALUES(?, ?);";
            PreparedStatement insert = connection.prepareStatement(
                    insertQuery,
                    Statement.RETURN_GENERATED_KEYS);
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
        String country;
        String name;
        try {
            get = connection.createStatement();
            ResultSet resultSet = get.executeQuery(String.format(
                    "SELECT * FROM manufacturers WHERE id = %d;", id.longValue()));
            if (resultSet.next()) {
                id = resultSet.getLong("id");
                name = resultSet.getString("name");
                country = resultSet.getString("country");
                manufacturer.setId(id);
                manufacturer.setName(name);
                manufacturer.setCountry(country);
            }
            return Optional.of(manufacturer);
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't get all formats from db");
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        Statement getAll;
        List<Manufacturer> result = new ArrayList<>();
        long id;
        String country;
        String name;
        try {
            getAll = connection.createStatement();
            ResultSet resultSet = getAll.executeQuery(
                    "SELECT * FROM manufacturers;");
            while (resultSet.next()) {
                id = resultSet.getLong("id");
                name = resultSet.getString("name");
                country = resultSet.getString("country");
                manufacturer.setId(id);
                manufacturer.setName(name);
                manufacturer.setCountry(country);
                result.add(manufacturer);
            }
            return result;
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't get all formats from db");
        }
    }

    @Override
    public Optional<Manufacturer> update(Manufacturer manufacturer) {
        try {
            String updateQuery = "UPDATE manufacturers SET name = ?,"
                    + " country = ? WHERE id = ?;";
            PreparedStatement preparedStatement =
                    connection.prepareStatement(updateQuery,
                            Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.setLong(3, manufacturer.getId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                this.manufacturer.setName(resultSet.getString("name"));
                this.manufacturer.setCountry(resultSet.getString("country"));
            }
            return Optional.of(this.manufacturer);
        } catch (SQLException throwables) {
            throw new RuntimeException(
                    "Can't update element here: " + manufacturer.getId() + " index");
        }
    }

    @Override
    public boolean delete(Long columnId) {
        try {
            String updateQuary = "UPDATE manufacturers"
                    + " SET is_deleted = true WHERE id = ?;";
            PreparedStatement preparedStatement =
                    connection.prepareStatement(updateQuary,
                            Statement.RETURN_GENERATED_KEYS);
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
}
