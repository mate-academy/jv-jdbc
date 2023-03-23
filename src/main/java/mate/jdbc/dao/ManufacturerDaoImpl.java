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
    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";
    private static final String COUNTRY_COLUMN = "country";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturer = "INSERT INTO manufacturers (name, country) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createStatement = connection
                        .prepareStatement(insertManufacturer,Statement.RETURN_GENERATED_KEYS)) {

            createStatement.setString(1,manufacturer.getName());
            createStatement.setString(2,manufacturer.getCountry());
            createStatement.executeUpdate();

            ResultSet generateKeys =
                    createStatement.getGeneratedKeys();
            if (generateKeys.next()) {
                Long id = generateKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add to DB: " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturer = "SELECT * FROM manufacturers WHERE is_deleted = FALSE AND id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllStatement = connection
                        .prepareStatement(getManufacturer)) {
            getAllStatement.setLong(1,id);
            ResultSet resultSet = getAllStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(createManufacturer(resultSet));
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer with id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllStatement = connection.createStatement()) {
            ResultSet resultSet = getAllStatement
                    .executeQuery("SELECT * FROM manufacturers WHERE is_deleted = FALSE;");
            while (resultSet.next()) {
                manufacturers.add(createManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get list of manufacturers from db", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturer = "UPDATE manufacturers SET name = ?, country = ?"
                + " WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createStatement = connection
                        .prepareStatement(updateManufacturer)) {
            createStatement.setString(1,manufacturer.getName());
            createStatement.setString(2,manufacturer.getCountry());
            createStatement.setLong(3,manufacturer.getId());
            createStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer in DB: "
                    + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = TRUE"
                + " WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createStatement = connection
                        .prepareStatement(deleteRequest,Statement.RETURN_GENERATED_KEYS)) {
            createStatement.setLong(1,id);
            return createStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer by id: " + id, e);
        }
    }

    private Manufacturer createManufacturer(ResultSet resultSet) {
        try {
            Long id = resultSet.getObject(ID_COLUMN,Long.class);
            String name = resultSet.getString(NAME_COLUMN);
            String country = resultSet.getString(COUNTRY_COLUMN);
            return new Manufacturer(id,name,country);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't parse manufacturer data from result set: "
                    + resultSet, e);
        }
    }
}
