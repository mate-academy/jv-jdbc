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
        Connection connect = ConnectionUtil.getConnect();
        try (Statement statement = connect.createStatement()) {
            ResultSet resultSet = statement
                    .executeQuery("SELECT id, name, country "
                            + "FROM Manufacturer where id_delete = false");
            while (resultSet.next()) {
                Manufacturer manufacturer = new Manufacturer();
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
        String updateQuery = "SELECT id,name,country FROM Manufacturer where id = ";
        try (Connection connect = ConnectionUtil.getConnect();) {
            Statement statement = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = statement.executeQuery(updateQuery + manufacturer.getId());
            while (resultSet.next()) {
                resultSet.updateString(2, manufacturer.getName());
                resultSet.updateRow();
                resultSet.updateString(3, manufacturer.getCountry());
                resultSet.updateRow();
            }
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
