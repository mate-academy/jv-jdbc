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
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String createStatementSql =
                "INSERT INTO manufacturers(name, country) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createStatement =
                        connection.prepareStatement(createStatementSql,
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
            throw new RuntimeException("Cant insert manufacturer to DB " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getByIdStatementSql =
                "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getByIdStatement
                        = connection.prepareStatement(getByIdStatementSql)) {
            getByIdStatement.setLong(1, id);
            ResultSet resultSet = getByIdStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getManufacturer(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Cant get manufacturer by id from DB " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String getAllStatementSql =
                "SELECT * FROM manufacturers WHERE is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllStatement =
                        connection.prepareStatement(getAllStatementSql)) {
            ResultSet resultSet = getAllStatement.executeQuery();
            while (resultSet.next()) {
                manufacturers.add(getManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cant get manufacturer by id from DB", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateStatementSql =
                "UPDATE manufacturers"
                        + " SET name = ?, country = ? WHERE id = ? AND id_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement =
                        connection.prepareStatement(updateStatementSql)) {
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.setLong(3, manufacturer.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return get(manufacturer.getId()).orElse(null);
    }

    @Override
    public boolean delete(Long id) {
        String deleteByIdStatementSql =
                "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteByIdStatement =
                        connection.prepareStatement(deleteByIdStatementSql)) {
            deleteByIdStatement.setLong(1, id);
            return deleteByIdStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new RuntimeException("Cant delete manufacturer by id from DB " + id, e);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) {
        try {
            String name = resultSet.getString("name");
            String country = resultSet.getString("country");
            Long manufacturersId = resultSet.getObject("id", Long.class);
            Manufacturer manufacturer = new Manufacturer(name, country);
            manufacturer.setId(manufacturersId);
            return manufacturer;
        } catch (SQLException e) {
            throw new RuntimeException("Cant get manufacturer from ResultSet" + resultSet, e);
        }
    }
}
