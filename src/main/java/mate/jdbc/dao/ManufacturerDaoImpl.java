package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.exceptions.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String COUNTRY = "country";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement createStatement =
                         connection.prepareStatement(
                             "INSERT INTO manufacturers(name,country) values(?,?)",
                             Statement.RETURN_GENERATED_KEYS)) {
            createStatement.setString(1, manufacturer.getName());
            createStatement.setString(2, manufacturer.getCountry());
            createStatement.executeUpdate();
            ResultSet generatedKeys = createStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't evaluate  create() for  manufacturer: "
                    + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getStatement =
                         connection.prepareStatement(
                             "SELECT * FROM manufacturers WHERE id=? AND is_deleted = FALSE")) {
            getStatement.setString(1, String.valueOf(id));
            ResultSet resultSet = getStatement.executeQuery();
            if (resultSet.next()) {
                Manufacturer manufacturer = getManufacturer(resultSet);
                return Optional.of(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't evaluate get() for id: "
                    + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        ArrayList<Manufacturer> manufacturerArrayList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getStatement =
                        connection.prepareStatement(
                             "SELECT * FROM manufacturers WHERE is_deleted = FALSE ")) {
            ResultSet resultSet = getStatement.executeQuery();
            while (resultSet.next()) {
                Manufacturer manufacturer = getManufacturer(resultSet);
                manufacturerArrayList.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't evaluate getALL() Manufacturers", e);
        }
        return manufacturerArrayList;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement =
                        connection.prepareStatement(
                             "UPDATE manufacturers SET name = ?, country = ? WHERE id=?")) {
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.setLong(3, manufacturer.getId());
            updateStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't evaluate  update() for  manufacturer: "
                    + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteStatement =
                        connection.prepareStatement(
                             "UPDATE manufacturers SET is_deleted = TRUE WHERE id=?")) {
            deleteStatement.setLong(1, id);
            return deleteStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't evaluate  update()."
                    + " There is no element if DB with id: ", e);
        }
    }

    private static Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getObject(ID, Long.class));
        manufacturer.setName(resultSet.getString(NAME));
        manufacturer.setCountry(resultSet.getString(COUNTRY));
        return manufacturer;
    }
}
