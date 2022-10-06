package mate.jdbc.dao;


import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionDb;
import java.sql.*;
import java.util.List;
import java.util.Optional;

@Dao
public class ManafacturerDaoImpl implements ManufacturerDao{
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String SQLRequest = "INSERT INTO manufacturers(name, country)"
                + " values(?,?)";
        try (Connection connection = ConnectionDb.getConnection();
             PreparedStatement createStatement =
                     connection.prepareStatement(SQLRequest,
                             Statement.RETURN_GENERATED_KEYS)) {
            createStatement.setString(1, manufacturer.getName());
            createStatement.setString(2, manufacturer.getCountry());
            createStatement.executeUpdate();
            ResultSet generatedKeys = createStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create manufacturer" + manufacturer, e);
        }
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
