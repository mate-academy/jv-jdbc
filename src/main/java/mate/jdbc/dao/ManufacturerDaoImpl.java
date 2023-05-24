package mate.jdbc.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

import static com.mysql.cj.util.SaslPrep.StringType.QUERY;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String query = "SELECT `id`, `name`, `country` from `manufacturer` "
                + "WHERE `is_deleted` = false;";

        try (Connection connection = ConnectionUtil.getConnection();
             Statement statement = connection.createStatement();) {
            ResultSet resultSet = statement.executeQuery(query);
            Manufacturer manufacturer;
            while (resultSet.next()) {
                manufacturer = new Manufacturer();
                Long id = resultSet.getObject("id", Long.class);
                manufacturer.setId(id);
                manufacturer.setName(resultSet.getString("name"));
                manufacturer.setCountry(resultSet.getString("country"));
                manufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        final String CREATE_QUERY =
                "INSERT INTO `taxi_service`.`manufacturer` (`name`, `country`) VALUES (?, ?);";
        Optional<Manufacturer> optionalManufacturer = Optional.empty();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement createPreparedStatement =
                     connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            createPreparedStatement.setString(1, manufacturer.getName());
            createPreparedStatement.setString(2, manufacturer.getCountry());
            createPreparedStatement.executeUpdate();
            ResultSet GeneratedKeys = createPreparedStatement.getGeneratedKeys();
            if (GeneratedKeys.next()) {
                Long id = GeneratedKeys.getObject(1, Long.class);
                optionalManufacturer = get(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return optionalManufacturer.get();
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        final String QUERY_GET_MANUFACTURER_BY_ID =
                "SELECT `name`, `country` from `manufacturer` "
                + "WHERE `is_deleted` = false and `id` = ?;";
        Optional<Manufacturer> optionalManufacturer = Optional.empty();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedGet =
                     connection.prepareStatement(QUERY_GET_MANUFACTURER_BY_ID)) {
            preparedGet.setLong(1, id);
        ResultSet resultSetManufacturer = preparedGet.executeQuery();
        if (resultSetManufacturer.next()) {
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setId(id);
            manufacturer.setName(resultSetManufacturer.getString("name"));
            manufacturer.setCountry(resultSetManufacturer.getString("country"));
            optionalManufacturer = Optional.of(manufacturer);
        }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return optionalManufacturer;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }
}
