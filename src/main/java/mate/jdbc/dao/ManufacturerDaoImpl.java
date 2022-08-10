package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import util.ConnectionUtill;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String textSql = "INSERT INTO manufacturers(name, country) values (?, ?);";
        try (Connection connection = ConnectionUtill.getConnection()) {
            PreparedStatement preparedStatement
                    = connection.prepareStatement(textSql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Long id = resultSet.getObject(1, Long.class);
                manufacturer.setId(id);
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new RuntimeException("Can't insert manufacturer " + manufacturer + " to DB", e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String textSql = "SELECT * FROM manufacturers WHERE NOT is_deleted AND id = " + id;
        try (Connection connection = ConnectionUtill.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(textSql);
            Manufacturer manufacturer = new Manufacturer();
            if (resultSet.next()) {
                manufacturer.setId(resultSet.getObject(1, Long.class));
                manufacturer.setName(resultSet.getString(2));
                manufacturer.setCountry(resultSet.getString(3));
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new RuntimeException("Can't insert manufacturer to DB by id: " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String textSql = "SELECT * FROM manufacturers where NOT is_deleted;";
        try (Connection connection = ConnectionUtill.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(textSql);
            List<Manufacturer> manufacturerList = new ArrayList<>();
            while (resultSet.next()) {
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(resultSet.getObject(1, Long.class));
                manufacturer.setName(resultSet.getString(2));
                manufacturer.setCountry(resultSet.getString(3));
                manufacturerList.add(manufacturer);
            }
            return manufacturerList;
        } catch (SQLException e) {
            throw new RuntimeException("Can't get all data of manufacturers", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String textSql = "UPDATE manufacturers SET name = ?, country = ? WHERE id = ?;";
        try (Connection connection = ConnectionUtill.getConnection()) {
            PreparedStatement updateStatement
                    = connection.prepareStatement(textSql);
            updateStatement.setLong(3, manufacturer.getId());
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new RuntimeException("Can't update manufacturer " + manufacturer + " to DB", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String textSql = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtill.getConnection()) {
            PreparedStatement deleteStatement
                    = connection.prepareStatement(textSql, Statement.RETURN_GENERATED_KEYS);
            deleteStatement.setLong(1, id);
            return deleteStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete manufacturer by id: " + id, e);
        }
    }
}
