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
    private static final String TABLE_NAME = "manufacturers";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String COUNTRY = "country";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertRequest = "INSERT INTO manufacturers(name, country) VALUES(?, ?)";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createStatement = connection.prepareStatement(insertRequest,
                        Statement.RETURN_GENERATED_KEYS)) {
            createStatement.setString(1, manufacturer.getName());
            createStatement.setString(2, manufacturer.getCountry());
            createStatement.executeUpdate();
            ResultSet generatedKey = createStatement.getGeneratedKeys();
            if (generatedKey.next()) {
                manufacturer.setId(generatedKey.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t create manufacturer" + manufacturer
                    + "in table: " + TABLE_NAME, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getByIdRequest = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = FALSE";
        Manufacturer manufacturer = new Manufacturer();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getByIDStatement = connection.prepareStatement(getByIdRequest)) {
            getByIDStatement.setLong(1, id);
            ResultSet resultSet = getByIDStatement.executeQuery();
            if (resultSet.next()) {
                manufacturer = getManufacture(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get manufacturer: " + manufacturer
                    + " from table: " + TABLE_NAME, e);
        }
        return Optional.of(manufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllRequest = "SELECT * FROM manufacturers WHERE is_deleted = FALSE";
        List<Manufacturer> manufacturerList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getAllStatement = connection.prepareStatement(getAllRequest)) {
            ResultSet resultSet = getAllStatement.executeQuery();
            while (resultSet.next()) {
                manufacturerList.add(getManufacture(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all manufacturers from table: "
                    + TABLE_NAME, e);
        }
        return manufacturerList;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET name = ?, country = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement updateByIdStatement = connection
                         .prepareStatement(updateRequest)) {
            updateByIdStatement.setString(1, manufacturer.getName());
            updateByIdStatement.setString(2, manufacturer.getCountry());
            updateByIdStatement.setLong(3, manufacturer.getId());
            updateByIdStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update manufacturer: " + manufacturer
                    + ", in table: " + TABLE_NAME, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement deleteByIdStatement = connection
                         .prepareStatement(deleteRequest)) {
            deleteByIdStatement.setLong(1, id);
            return deleteByIdStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete manufacturer by id: " + id
                    + " from table: " + TABLE_NAME, e);
        }
    }

    private Manufacturer getManufacture(ResultSet resultSet) {
        try {
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setId(resultSet.getObject(ID, Long.class));
            manufacturer.setName((String) resultSet.getObject(NAME));
            manufacturer.setCountry((String) resultSet.getObject(COUNTRY));
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get manufacturer from resultSet ", e);
        }
    }
}
