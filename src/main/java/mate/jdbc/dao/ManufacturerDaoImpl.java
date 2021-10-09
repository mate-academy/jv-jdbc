package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import mate.jdbc.lib.Dao;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
             Statement createManufacturerStatement = connection.createStatement()) {
            String insertManufacturerRequest = "INSERT INTO manufacturers (name, country) values('"
                    + manufacturer.getName() + "','"
                    + manufacturer.getCountry() + "');";
            createManufacturerStatement.executeUpdate(insertManufacturerRequest);
        } catch (SQLException e) {
            throw new DataProcessingException("Can not insert manufacturer to DB!!!", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
             Statement getManufacturerStatement = connection.createStatement()) {
            String getManufacturerRequest =
                    "SELECT * FROM manufacturers WHERE is_deleted = 0 && id = "
                            + id + ";";
            ResultSet resultSet = getManufacturerStatement.executeQuery(getManufacturerRequest);
            resultSet.next();
            String manufacturer = resultSet.getString("name");
            String country = resultSet.getString("country");
            Manufacturer manufacturerFromDb = new Manufacturer();
            manufacturerFromDb.setId(id);
            manufacturerFromDb.setName(manufacturer);
            manufacturerFromDb.setCountry(country);
            return Optional.of(manufacturerFromDb);
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             Statement getAllManufacturersStatement = connection.createStatement()) {
            ResultSet resultSet =
                    getAllManufacturersStatement
                            .executeQuery("SELECT * FROM manufacturers WHERE is_deleted = 0;");
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String manufacturer = resultSet.getString("name");
                String country = resultSet.getString("country");
                Manufacturer manufacturerFromDb = new Manufacturer();
                manufacturerFromDb.setId(id);
                manufacturerFromDb.setName(manufacturer);
                manufacturerFromDb.setCountry(country);
                manufacturers.add(manufacturerFromDb);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get manufacturers from DB!!!", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
             Statement updateManufacturerStatement = connection.createStatement()) {
            String updateManufacturerRequest =
                    "UPDATE manufacturers SET name = '" + manufacturer.getName()
                            + "', country = '" + manufacturer.getCountry()
                            + "' WHERE id = " + manufacturer.getId() + ";";
            updateManufacturerStatement.executeUpdate(updateManufacturerRequest);
            return get(manufacturer.getId()).get();
        } catch (SQLException e) {
            throw new DataProcessingException("Can not update manufacturer in DB", e);
        } catch (NoSuchElementException e) {
            throw new DataProcessingException("This manufacturer not found in DB ", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
             Statement deleteManufacturerStatement = connection.createStatement()) {
            if (!new ManufacturerDaoImpl().get(id).isPresent()) {
                return false;
            }
            String deleteManufacturerRequest = "UPDATE manufacturers SET is_deleted = TRUE WHERE id = " + id + ";";
            deleteManufacturerStatement.executeUpdate(deleteManufacturerRequest);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
