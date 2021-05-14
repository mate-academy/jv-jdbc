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
        String getRequest =
                "INSERT INTO manufacturers(manufacturer_name, manufacturer_country) values(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement =
                         connection.prepareStatement(getRequest,
                             Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                manufacturer.setId(generatedKeys.getObject(1, Long.class));
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t create new record with"
                    + " manufacturer_name = " + manufacturer.getName()
                    + ", manufacturer_country = " + manufacturer.getCountry() , e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String updateRequest =
                "UPDATE manufacturers SET is_deleted = true WHERE manufacturer_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement deleteManufacturer =
                         connection.prepareStatement(updateRequest)) {
            deleteManufacturer.setLong(1, id);
            return deleteManufacturer.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete manufacturer with id = " + id, e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getRequest =
                "SELECT * FROM manufacturers WHERE manufacturer_id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getManufacturer =
                         connection.prepareStatement(getRequest)) {
            getManufacturer.setLong(1, id);
            ResultSet resultSet = getManufacturer.executeQuery();
            Manufacturer manufacturer = null;
            if (resultSet.next()) {
                manufacturer = parseResultSet(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get manufacturer with id = " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String getRequest =
                "SELECT * FROM manufacturers WHERE is_deleted = false";
        try (Connection connection =
                     ConnectionUtil.getConnection();
                 Statement getAllManufacturersStatement = connection.createStatement()) {
            ResultSet resultSet =
                    getAllManufacturersStatement.executeQuery(getRequest);
            List<Manufacturer> allManufacturersList = new ArrayList<>();
            while (resultSet.next()) {
                allManufacturersList.add(parseResultSet(resultSet));
            }
            return allManufacturersList;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all manufacturers from DB", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest =
                "UPDATE manufacturers SET manufacturer_name = ?, manufacturer_country = ?"
                        + "WHERE manufacturer_id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement updateManufacturerStatement =
                         connection.prepareStatement(updateRequest)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            if (updateManufacturerStatement.executeUpdate() > 0) {
                return manufacturer;
            }
            throw new RuntimeException("Can`t find record with id = " + manufacturer.getId());
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update record with id = "
                    + manufacturer.getId(), e);
        }
    }

    private Manufacturer parseResultSet(ResultSet resultSet) {
        try {
            Long manufacturerId = resultSet.getObject("manufacturer_id", Long.class);
            String manufacturerName = resultSet.getString("manufacturer_name");
            String manufacturerCountry =
                    resultSet.getString("manufacturer_country");
            return new Manufacturer(manufacturerId, manufacturerName, manufacturerCountry);
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t parse the resultSet!", e);
        }
    }
}
