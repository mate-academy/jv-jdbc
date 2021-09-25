package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerStatement =
                "INSERT INTO manufacturers(name, country) VALUES (?, ?);";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement =
                        connection.prepareStatement(insertManufacturerStatement,
                            Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new RuntimeException("Can insert manufacturer to DB!", e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String selectManufacturerStatement =
                String.format("SELECT * FROM manufacturers WHERE id = %d AND is_deleted = FALSE;",
                        id);
        try (Connection connection = ConnectionUtil.getConnection();
                Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(selectManufacturerStatement);
            if (resultSet.next()) {
                return Optional.of(getResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get information according id from DB!");
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> resultList = new ArrayList<>();
        String selectManufacturerStatement =
                "SELECT * FROM manufacturers WHERE is_deleted = FALSE;";

        try (Connection connection = ConnectionUtil.getConnection();
                Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(selectManufacturerStatement);
            while (resultSet.next()) {
                resultList.add(getResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get all info from DB", e);
        }
        return resultList;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        Long id = manufacturer.getId();
        String updateStatement =
                "UPDATE manufacturers SET name = ?, country = ?, is_deleted = FALSE WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement =
                        connection.prepareStatement(updateStatement)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setObject(3, id);
            updateManufacturerStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can't update info in DB!", e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteIdStatement = "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturersStatement =
                        connection.prepareStatement(deleteIdStatement)) {
            deleteManufacturersStatement.setObject(1, id);
            int updatedRows = deleteManufacturersStatement.executeUpdate();
            return updatedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete in manufacturers!");
        }
    }

    private Manufacturer getResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        Manufacturer manufacturer = new Manufacturer(name, country);
        manufacturer.setId(id);
        return manufacturer;
    }
}
