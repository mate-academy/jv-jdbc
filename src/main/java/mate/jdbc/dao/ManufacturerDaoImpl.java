package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertFormat = "INSERT INTO manufacturers(name,country) values(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturersStatement =
                        connection.prepareStatement(insertFormat,
                                Statement.RETURN_GENERATED_KEYS)) {
            createManufacturersStatement.setString(1, manufacturer.getName());
            createManufacturersStatement.setString(2, manufacturer.getCountry());
            createManufacturersStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturersStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't create manufacturer to DB ", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getRequest = "SELECT * FROM manufacturers WHERE is_deleted = false AND id =" + id;
        Manufacturer manufacturer = new Manufacturer();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement
                        = connection.prepareStatement(getRequest)) {
            ResultSet resultSet = getManufacturerStatement.executeQuery(getRequest);
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                manufacturer.setName(name);
                manufacturer.setCountry(country);
                manufacturer.setId(id);
            }
            return Optional.of(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find manufacturer from DB with this id: "
                    + id + " ", e);
        }

    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturer = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllManufacturersStatement = connection.createStatement()
        ) {
            ResultSet resultSet = getAllManufacturersStatement
                            .executeQuery("SELECT * FROM manufacturers WHERE is_deleted = false");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Long id = resultSet.getObject("id", Long.class);
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setName(name);
                manufacturer.setCountry(country);
                manufacturer.setId(id);
                allManufacturer.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB ", e);
        }
        return allManufacturer;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        Manufacturer optionalManufacturer
                = get(manufacturer.getId()).orElseThrow(RuntimeException::new);
        String updateRequest = "UPDATE manufacturers SET name = ?, country = ?"
                        + " WHERE is_deleted = false AND id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturersStatement
                        = connection.prepareStatement(updateRequest)) {
            createManufacturersStatement.setLong(3, manufacturer.getId());
            createManufacturersStatement.setString(1,manufacturer.getName());
            createManufacturersStatement.setString(2,manufacturer.getCountry());
            optionalManufacturer.setName(manufacturer.getName());
            optionalManufacturer.setCountry(manufacturer.getCountry());
            createManufacturersStatement.executeUpdate();
            return optionalManufacturer;
        } catch (SQLException e) {
            throw new RuntimeException("Can't insert manufacturers from DB ", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturersStatement
                        = connection.prepareStatement(deleteRequest)) {
            createManufacturersStatement.setLong(1, id);
            return createManufacturersStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete this manufacturer"
                    + " from DB with this id: " + id + " ",e);
        }
    }
}
