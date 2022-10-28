package mate.jdbc.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.DataProcessingException;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl extends ConnectionUtil implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String query = "INSERT INTO manufacturers(name,country) VALUES(?,?);";
        System.out.println(query);
        try (PreparedStatement createManufacturer = getConnection()
                .prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            createManufacturer.setString(1, manufacturer.getName());
            createManufacturer.setString(2, manufacturer.getCountry());
            createManufacturer.executeUpdate();
            ResultSet resultSet = createManufacturer.getGeneratedKeys();
            if (resultSet.next()) {
                Long id = resultSet.getObject(1, Long.class);
                manufacturer.setId(resultSet.getObject(1, Long.class));
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException(
                    "Can't insert manufacturer " + manufacturer, throwables);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String sql = "SELECT * FROM manufacturers WHERE id = "
                + id + " AND " + "Is_deleted = false" + ";";
        System.out.println(sql);
        Manufacturer manufacturer = null;
        try (Statement statement = getConnection().createStatement();
                ResultSet resultset = statement.executeQuery(sql)) {
            if (resultset.next()) {
                manufacturer = getManufacturer(resultset);
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't get all manufacturers", throwables);
        }
        return Optional.of(manufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {
        String sql = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Statement statement = getConnection().createStatement();
                ResultSet resultset = statement.executeQuery(sql)) {
            while ((resultset.next())) {
                allManufacturers.add(getManufacturer(resultset));
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't get all manufacturers", throwables);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String sql = "UPDATE manufacturers SET "
                + " name = " + "'" + manufacturer.getName() + "', "
                + "country =" + "'" + manufacturer.getCountry() + "' "
                + "where id = " + manufacturer.getId() + ";";
        System.out.println(sql);
        try (Statement statement = getConnection().createStatement()) {
            int number = statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throw new DataProcessingException(
                    "Can't update manufacturer " + manufacturer, throwables);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String sql = "UPDATE manufacturers SET is_deleted = true WHERE id=" + id + ";";
        try (Statement statement = getConnection().createStatement()) {
            int number = statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't delete manufacturer bei id " + id, throwables);
        }
        return false;
    }

    private Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        return new Manufacturer(id, name, country);
    }
}
