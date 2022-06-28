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
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {

    private Connection connection = ConnectionUtil.getConnection();

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();

        try (Statement getManufacturerStatement = connection.createStatement();) {
            ResultSet resultSet = getManufacturerStatement
                    .executeQuery("SELECT * from manufacturers WHERE is_deleted = FALSE");
            while (resultSet.next()) {
                Long id = resultSet.getObject("id",Long.class);
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Manufacturer manufacturerObj = new Manufacturer();
                manufacturerObj.setId(id);
                manufacturerObj.setName(name);
                manufacturerObj.setCountry(country);
                allManufacturers.add(manufacturerObj);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get data from DB",e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertFormatRequest = "INSERT INTO manufacturers(name,country) values(?,?)";
        //Connection connection = ConnectionUtil.getConnection();
        //
        try (PreparedStatement createManufacturerStatement = connection
                .prepareStatement(insertFormatRequest,Statement.RETURN_GENERATED_KEYS);) {
            createManufacturerStatement.setString(1,manufacturer.getName());
            createManufacturerStatement.setString(2,manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1,Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't insert data to DB",e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufactureRequest = "UPDATE manufacturers set is_deleted = TRUE where id = ?";
        //Connection connection = ConnectionUtil.getConnection();
        //
        try (PreparedStatement createManufacturerStatement = connection
                .prepareStatement(deleteManufactureRequest,Statement.RETURN_GENERATED_KEYS);) {
            createManufacturerStatement.setLong(1,id);
            return createManufacturerStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete data from DB",e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufactureRequest =
                "SELECT * from manufacturers WHERE is_deleted = FALSE AND id = ?";
        //Connection connection = ConnectionUtil.getConnection();
        try (PreparedStatement getManufacturerStatement = connection
                .prepareStatement(getManufactureRequest,Statement.RETURN_GENERATED_KEYS);) {
            getManufacturerStatement.setLong(1,id);
            ResultSet getResultSet = getManufacturerStatement.executeQuery();
            if (getResultSet.next()) {
                Manufacturer manufacture = new Manufacturer();
                manufacture.setId(getResultSet.getObject("id",Long.class));
                manufacture.setName(getResultSet.getObject("name",String.class));
                manufacture.setCountry(getResultSet.getObject("country",String.class));
                return Optional.of(manufacture);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete data from DB",e);
        }
        return Optional.empty();
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String insertFormatRequest = "UPDATE manufacturers set name=?, country=? WHERE id=?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement = connection
                        .prepareStatement(insertFormatRequest,Statement.RETURN_GENERATED_KEYS);) {
            createManufacturerStatement.setString(1,manufacturer.getName());
            createManufacturerStatement.setString(2,manufacturer.getCountry());
            createManufacturerStatement.setLong(3,manufacturer.getId());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1,Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't insert data to DB",e);
        }
        return manufacturer;
    }

}
