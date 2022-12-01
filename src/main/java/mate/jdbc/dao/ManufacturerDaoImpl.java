package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import mate.jdbc.lib.Dao;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectTo_DB;
import mate.jdbc.util.ConnectTo_MySQL;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    ConnectTo_DB connectTo_db;

    public ManufacturerDaoImpl() {
        connectTo_db = new ConnectTo_MySQL();
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String createQueryString = "INSERT INTO manufacturers(name, country) values(?, ?)";
        try (Connection connection = connectTo_db.getConnection();
             PreparedStatement createRecordStatement =
                     connection.prepareStatement(createQueryString, PreparedStatement.RETURN_GENERATED_KEYS)) {
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
        return null;
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
