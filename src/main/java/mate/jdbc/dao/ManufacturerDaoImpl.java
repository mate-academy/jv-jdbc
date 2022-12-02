package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.lib.Dao;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectToDB;
import mate.jdbc.util.ConnectToMySql;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private final ConnectToDB connectToDB;

    public ManufacturerDaoImpl() {
        connectToDB = new ConnectToMySql();
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String createQueryString = "INSERT INTO manufacturers(name, country) values(?, ?)";
        try (Connection connection = connectToDB.getConnection();
                PreparedStatement createRecordStatement =
                        connection.prepareStatement(createQueryString,
                                                    PreparedStatement.RETURN_GENERATED_KEYS)) {
            createRecordStatement.setString(1, manufacturer.getName());
            createRecordStatement.setString(2, manufacturer.getCountry());
            createRecordStatement.executeUpdate();
            ResultSet generatedKeys = createRecordStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                manufacturer.setId(generatedKeys.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can`t insert record to manufacturers table", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = null;
        String getAllQueryString = "SELECT * FROM manufacturers";
        try (Connection connection = connectToDB.getConnection();
             PreparedStatement getAllRecordsStatement =
                     connection.prepareStatement(getAllQueryString)) {
            ResultSet resultSet = getAllRecordsStatement.executeQuery();
            manufacturers = new ArrayList<>();
            Manufacturer.Builder builder = new Manufacturer.Builder();
            while (resultSet.next()) {
                manufacturers.add(builder.setId(resultSet.getObject("id", Long.class))
                                         .setName(resultSet.getString("name"))
                                         .setCountry(resultSet.getString("country")).build());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can`t get records from DB", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
