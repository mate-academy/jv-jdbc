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
        String sql = "insert into manufacturers(name,country) values("
                + "'" + manufacturer.getName()
                + "'," + "'" + manufacturer.getCountry() + "');";
        System.out.println(sql);
        try (PreparedStatement createManufacturer = getConnection()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            createManufacturer.executeUpdate();
            ResultSet resultSet = createManufacturer.getGeneratedKeys();
            if (resultSet.next()) {
                Long id = resultSet.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException(
                    "Can't insert manufacturer " + manufacturer, throwables);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String sql = "SELECT * FROM manufacturers where id = "
                + id + " and " + "Is_deleted = false" + ";";
        System.out.println(sql);
        Optional<Manufacturer> manufacturer = Optional.empty();
        try (Statement statement = getConnection().createStatement();
                ResultSet resultset = statement.executeQuery(sql)) {
            if (resultset.next()) {
                Long idMan = resultset.getObject(1, Long.class);
                String name = resultset.getString(2);
                String country = resultset.getString(3);
                manufacturer = Optional.of(new Manufacturer(idMan, name, country));
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't get all manufacturers", throwables);
        }
        return manufacturer;
    }

    @Override
    public List<Manufacturer> getAll() {
        String sql = "select * from manufacturers where is_deleted = false;";
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Statement statement = getConnection().createStatement();
                ResultSet resultset = statement.executeQuery(sql)) {
            while ((resultset.next())) {
                Long id = resultset.getObject(1, Long.class);
                String name = resultset.getString(2);
                String country = resultset.getString(3);
                Manufacturer manufacturer = new Manufacturer(id, name, country);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't get all manufacturers", throwables);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String sql = "update manufacturers set "
                + " name = " + "'" + manufacturer.getName() + "', "
                + "country =" + "'" + manufacturer.getCountry() + "' "
                + "where id = " + manufacturer.getId() + ";";
        System.out.println(sql);
        try (Statement statement = getConnection().createStatement()) {
            int number = statement.executeUpdate(sql);
            if (number == 1) {
                return manufacturer;
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException(
                    "Can't update manufacturer " + manufacturer, throwables);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        String sql = "update manufacturers set is_deleted = true where id=" + id + ";";
        try (Statement statement = getConnection().createStatement()) {
            int number = statement.executeUpdate(sql);
            if (number == 1) {
                return true;
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't delete manufacturer bei id " + id, throwables);
        }
        return false;
    }
}
