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
import mate.jdbc.lib.util.ConnectionUtil;
import mate.jdbc.model.Manufacturer;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturerList = new ArrayList<>();
        Manufacturer manufacturer;
        String querySelect = "SELECT id, name, country "
                + "FROM Manufacturer where id_delete = false";
        try (Connection connect = ConnectionUtil.getConnect();) {
            PreparedStatement preparedStatement = connect.prepareStatement(querySelect);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                manufacturer = new Manufacturer();
                Long id = resultSet.getObject("id", Long.class);
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                manufacturer.setId(id);
                manufacturer.setName(name);
                manufacturer.setCountry(country);
                manufacturerList.add(manufacturer);
            }
        } catch (SQLException throwables) {
            throw new RuntimeException(" is not good connection method getAll ", throwables);
        }
        return manufacturerList;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String queryUpdate = "Update Manufacturer Set id = ?,name = ?, country = ? where id =";
        String querySelect = "SELECT id,name,country from Manufacturer where id =";
        try (Connection connect = ConnectionUtil.getConnect()) {
            PreparedStatement preparedStatement = connect
                    .prepareStatement(queryUpdate + manufacturer.getId());
            ResultSet resultSet = preparedStatement
                    .executeQuery(querySelect + manufacturer.getId());
            while (resultSet.next()) {
                preparedStatement.setLong(1, manufacturer.getId());
                preparedStatement.setString(2, manufacturer.getName());
                preparedStatement.setString(3, manufacturer.getCountry());
            }
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throw new RuntimeException(" is not good connection in method update ", throwable);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String queryDelete = "update Manufacturer set id_delete = true where id = ?";
        try (Connection connect = ConnectionUtil.getConnect();
        ) {
            PreparedStatement preparedStatement = connect.prepareStatement(queryDelete);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throw new RuntimeException("is not connection in method delete ", throwable);
        }
        return false;
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertInto = "insert into Manufacturer (id,name,country,id_delete) values (?,?,?,?)";
        try (Connection connect = ConnectionUtil.getConnect()) {
            PreparedStatement preparedStatement = connect.prepareStatement(insertInto);
            preparedStatement.setLong(1, manufacturer.getId());
            preparedStatement.setString(2, manufacturer.getName());
            preparedStatement.setString(3, manufacturer.getCountry());
            preparedStatement.setBoolean(4, false);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("is not good connection in method create", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Manufacturer manufacturer = new Manufacturer();
        String queryGet = "select id, name, country "
                + "from Manufacturer where id_delete = false and id =";
        try (Connection connect = ConnectionUtil.getConnect()) {
            Statement statement = connect.createStatement();
            ResultSet resultSet = statement
                    .executeQuery(queryGet + id);
            while (resultSet.next()) {
                Long idi = resultSet.getObject("id", Long.class);
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                manufacturer.setId(idi);
                manufacturer.setName(name);
                manufacturer.setCountry(country);
            }
        } catch (SQLException throwable) {
            throw new RuntimeException("is not good connection in method get", throwable);
        }
        return Optional.of(manufacturer);
    }
}
