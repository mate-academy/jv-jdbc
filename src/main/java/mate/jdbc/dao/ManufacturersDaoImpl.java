package mate.jdbc.dao;

import mate.jdbc.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class ManufacturersDaoImpl implements ManufacturersDao {
    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String getAllManufacturersRequest = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement getAllManufacturersStatement
                     = connection.prepareStatement(getAllManufacturersRequest)){
            ResultSet resultSet = getAllManufacturersStatement.executeQuery();
            if (resultSet == null) {
                return new ArrayList<>();
            }
            while (resultSet.next()) {
                Manufacturer manufacturer = retrieveData(resultSet);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all manufacturers", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest = "INSERT INTO manufacturers (name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement createManufacturerStatement
                     = connection.prepareStatement(insertManufacturerRequest,
                     Statement.RETURN_GENERATED_KEYS);
        ) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()){
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t create manufacturer", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        return null;
    }

    private Manufacturer retrieveData(ResultSet resultSet) {
        Manufacturer manufacturer = null;
        try {
            String name = resultSet.getString("name");
            String country = resultSet.getString("country");
            Long id = resultSet.getObject("id", Long.class);
            manufacturer = new Manufacturer();
            manufacturer.setName(name);
            manufacturer.setCountry(country);
            manufacturer.setId(id);
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t retrieve data from ResultSet", e);
        }
        return manufacturer;
    }
}
