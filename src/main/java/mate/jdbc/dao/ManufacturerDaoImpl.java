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
    public static final String NAME_COLUMN = "name";
    public static final String COUNTRY_COLUMN = "country";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        if (manufacturer == null) {
            return null;
        }
        String createManufacturerStatement = "INSERT INTO manufacturers(name, country)"
                + " VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnect();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(createManufacturerStatement,
                                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long key = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(key);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create manufacturer. "
                    + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturerStatement = "SELECT * FROM manufacturers "
                + "WHERE is_deleted = false AND id = ?;";
        try (Connection connection = ConnectionUtil.getConnect();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(getManufacturerStatement)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Manufacturer manufacturer = null;
            if (resultSet.next()) {
                manufacturer = parse(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer from DB. " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allData = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnect();
                Statement getAllDataStatements = connection.createStatement()) {
            ResultSet resultSet = getAllDataStatements.executeQuery("SELECT * FROM manufacturers "
                    + "WHERE is_deleted = false");
            while (resultSet.next()) {
                Manufacturer manufacturer = parse(resultSet);
                allData.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
        return allData;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        if (manufacturer == null) {
            return null;
        }
        String updateStatement = "UPDATE manufacturers SET name = ?,"
                + " country = ? WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnect();
                PreparedStatement statement = connection.prepareStatement(updateStatement)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.setLong(3, manufacturer.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteStatement = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnect();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(deleteStatement, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturers from DB " + id, e);
        }
    }

    private Manufacturer parse(ResultSet resultSet) {
        Manufacturer manufacture = new Manufacturer();
        try {
            manufacture.setId(resultSet.getObject(1, Long.class));
            manufacture.setName(resultSet.getString(NAME_COLUMN));
            manufacture.setCountry(resultSet.getString(COUNTRY_COLUMN));
        } catch (SQLException e) {
            throw new DataProcessingException("Can't parse ResultSet", e);
        }
        return manufacture;
    }
}
