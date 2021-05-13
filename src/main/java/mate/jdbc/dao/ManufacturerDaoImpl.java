package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.exeption.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String craterQuery = "INSERT INTO manufactures (name, country)  VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacture = connection.prepareStatement(craterQuery,
                        Statement.RETURN_GENERATED_KEYS)) {
            createManufacture.setString(1, manufacturer.getName());
            createManufacture.setString(2, manufacturer.getCountry());
            createManufacture.executeUpdate();
            ResultSet generateKye = createManufacture.getGeneratedKeys();
            if (generateKye.next()) {
                Long id = generateKye.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get information from DB!", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturerById = "SELECT * FROM manufactures "
                + "WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacture =
                        connection.prepareStatement(getManufacturerById)) {
            getManufacture.setLong(1, id);
            ResultSet resultSet = getManufacture.executeQuery();
            if (resultSet.next()) {
                Long currentId = resultSet.getObject("Id", Long.class);
                String name = resultSet.getString("Name");
                String country = resultSet.getString("Country");
                Manufacturer currentManufacture = new Manufacturer(currentId, name, country);
                return Optional.of(currentManufacture);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get information from DB!", e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturersList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllManufacturers = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturers.executeQuery("SELECT * FROM manufactures"
                    + " WHERE is_deleted = FALSE;");
            while (resultSet.next()) {
                Long id = resultSet.getObject("Id", Long.class);
                String name = resultSet.getString("Name");
                String country = resultSet.getString("Country");
                Manufacturer currentManufacture = new Manufacturer(id, name, country);
                manufacturersList.add(currentManufacture);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all information from DB!", e);
        }
        return manufacturersList;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String getManufacturerById = "UPDATE manufactures SET name = ?, country = ?"
                + " WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacture =
                        connection.prepareStatement(getManufacturerById)) {
            getManufacture.setString(1, manufacturer.getName());
            getManufacture.setString(2, manufacturer.getCountry());
            getManufacture.setLong(3, manufacturer.getId());
            if (getManufacture.executeUpdate() >= 1) {
                return manufacturer;
            }
            throw new DataProcessingException("Data isn't exist by index: " + manufacturer.getId());
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update information in DB!", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String getManufacturerById = "UPDATE manufactures SET is_deleted = TRUE "
                + "WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deletedManufacture =
                        connection.prepareStatement(getManufacturerById)) {
            deletedManufacture.setString(1, id.toString());
            return deletedManufacture.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get information from DB!", e);
        }
    }
}
