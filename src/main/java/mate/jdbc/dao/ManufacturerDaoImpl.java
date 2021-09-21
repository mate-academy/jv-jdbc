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
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertRequest = "INSERT INTO manufacturers(name,country) values(?,?);";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createStatement = connection.prepareStatement(insertRequest,
                        Statement.RETURN_GENERATED_KEYS)) {

            createStatement.setString(1,manufacturer.getName());
            createStatement.setString(2,manufacturer.getCountry());
            createStatement.executeUpdate();
            ResultSet generatedKeys = createStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1,Long.class);
                manufacturer.setId(id);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Can't create new row from DB",e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true where id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createFormatStatement = connection.prepareStatement(deleteRequest,
                        Statement.RETURN_GENERATED_KEYS)) {
            createFormatStatement.setObject(1,id);
            return createFormatStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete from DB " + id,e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateData = "UPDATE manufacturers SET name = ?, country = ? "
                + "WHERE id = ? AND is_deleted = 0";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement createStatement = connection.prepareStatement(updateData)) {

            createStatement.setString(1,manufacturer.getName());
            createStatement.setString(2,manufacturer.getCountry());
            createStatement.setObject(3,manufacturer.getId());
            createStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Can't update from DB",e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getRequest = "SELECT * FROM manufacturers WHERE is_deleted = 0 AND id = ?;";
        Manufacturer manufacturerGet = new Manufacturer();

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getStatement = connection.prepareStatement(getRequest)) {
            getStatement.setObject(1, id);
            ResultSet resultSet = getStatement.executeQuery();
            if (resultSet.next()) {
                manufacturerGet.setId(id);
                manufacturerGet.setName(resultSet.getString("name"));
                manufacturerGet.setCountry(resultSet.getString("country"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get manufacturer from DB by id: " + id, e);
        }
        return Optional.ofNullable(manufacturerGet);
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturer = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllFormatStatement = connection.createStatement()) {
            ResultSet resultSet = getAllFormatStatement.executeQuery(
                    "SELECT * FROM manufacturers WHERE is_deleted = 0");
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");

                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(id);
                manufacturer.setName(name);
                manufacturer.setCountry(country);

                allManufacturer.add(manufacturer);
                System.out.println(manufacturer);

            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get all Manufacturers from DB",e);
        }
        return allManufacturer;
    }

}
