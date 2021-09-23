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
                return getId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can insert manufacturer to DB!", e);
        }
        return null;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String selectManufacturerStatement =
                String.format("SELECT * FROM manufacturers WHERE id = %d AND is_deleted = FALSE;",
                        id);

        try (Connection connection = ConnectionUtil.getConnection();
                Statement getManufacturerStatement = connection.createStatement()) {
            ResultSet resultSet = getManufacturerStatement
                    .executeQuery(selectManufacturerStatement);
            if (resultSet.next()) {
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(id);
                manufacturer.setName(resultSet.getString("name"));
                manufacturer.setCountry(resultSet.getString("country"));
                return Optional.of(manufacturer);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get information according id from DB!");
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> resultList = new ArrayList<>();
        String selectAllManufacturerStatement =
                "SELECT * FROM manufacturers WHERE is_deleted = FALSE;";

        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllManufacturerStatement = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturerStatement
                    .executeQuery(selectAllManufacturerStatement);
            while (resultSet.next()) {
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(resultSet.getObject(1, Long.class));
                manufacturer.setName(resultSet.getString(2));
                manufacturer.setCountry(resultSet.getString(3));
                resultList.add(manufacturer);
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
                        connection.prepareStatement(updateStatement);) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setObject(3, id);
            updateManufacturerStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can't update info in DB!", e);
        }
        return getId(id);
    }

    @Override
    public boolean delete(Long id) {
        String deleteIdStatement = "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturersStatement =
                        connection.prepareStatement(deleteIdStatement);) {
            deleteManufacturersStatement.setObject(1, id);
            int updatedRows = deleteManufacturersStatement.executeUpdate();
            return updatedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete in manufacturers!");
        }
    }

    private Manufacturer getId(Long id) {
        Manufacturer updatedManufacturer = new Manufacturer();
        String selectIdStatement =
                String.format("SELECT name, country FROM manufacturers WHERE id = %d "
                        + "AND is_deleted = FALSE;", id);

        try (Connection connection = ConnectionUtil.getConnection();
                Statement selectIdManufacturerStatement = connection.createStatement()) {
            ResultSet resultSet = selectIdManufacturerStatement.executeQuery(selectIdStatement);
            if (resultSet.next()) {
                updatedManufacturer.setId(id);
                updatedManufacturer.setName(resultSet.getString("name"));
                updatedManufacturer.setCountry(resultSet.getString("country"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't select by id from DB in update method!", e);
        }
        return updatedManufacturer;
    }
}
