package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.excpetions.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String getAllRequest = "SELECT * FROM manufacturers WHERE is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturersStatement = connection
                        .prepareStatement(getAllRequest)) {
            ResultSet resultSet = getAllManufacturersStatement
                    .executeQuery();
            while (resultSet.next()) {
                manufacturers.add(createManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from db", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET name = ?, "
                + "country = ? WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement = connection.prepareStatement(updateRequest)) {
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.setLong(3, manufacturer.getId());
            if (updateStatement.executeUpdate() > 0) {
                return manufacturer;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer with id: "
                    + manufacturer.getId() + " from db to manufacturer: "
                    + manufacturer, e);
        }
        throw new DataProcessingException("Can't update manufacturer with id: "
                + manufacturer.getId() + " because he is already deleted");
    }

    @Override
    public Boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteStatement = connection.prepareStatement(deleteRequest)) {
            deleteStatement.setLong(1, id);
            return deleteStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer with id: "
                    + id + "from db", e);
        }
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertRequest = "INSERT INTO manufacturers(name, country) VALUES(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement insertStatement = connection
                         .prepareStatement(insertRequest, Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setString(1, manufacturer.getName());
            insertStatement.setString(2, manufacturer.getCountry());
            insertStatement.executeUpdate();
            ResultSet generatedKeys = insertStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                manufacturer.setId(generatedKeys.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer: "
                    + manufacturer + "to db", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getRequest = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getByIdStatement = connection.prepareStatement(getRequest)) {
            getByIdStatement.setLong(1, id);
            ResultSet resultSet = getByIdStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(createManufacturer(resultSet));
            } else {
                throw new DataProcessingException("Can't get manufacturer by id: "
                        + id + " from db, because he doesn't exist");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by id: "
                    + id + " from db", e);
        }
    }

    private Manufacturer createManufacturer(ResultSet manufacturerSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(manufacturerSet.getObject("id", Long.class));
        manufacturer.setName(manufacturerSet.getObject("name", String.class));
        manufacturer.setCountry(manufacturerSet.getObject("country", String.class));
        return manufacturer;
    }
}
