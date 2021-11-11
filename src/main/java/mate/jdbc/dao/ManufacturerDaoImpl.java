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
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerSql = "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement creatManufacturerStatement =
                        connection.prepareStatement(insertManufacturerSql,
                                Statement.RETURN_GENERATED_KEYS)) {
            creatManufacturerStatement.setString(1, manufacturer.getName());
            creatManufacturerStatement.setString(2, manufacturer.getCountry());
            creatManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = creatManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer to DB " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String selectManufacturerSql =
                "SELECT * FROM manufacturers WHERE is_deleted = false AND id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                     PreparedStatement selectManufacturerStatement =
                        connection.prepareStatement(selectManufacturerSql)) {
            selectManufacturerStatement.setLong(1, id);
            ResultSet resultSet = selectManufacturerStatement.executeQuery();
            if (resultSet.next()) {
                Manufacturer manufacturer = getManufacturerFromRS(resultSet);
                return Optional.of(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer from DB by ID: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        String selectManufacturerSql = "SELECT * FROM manufacturers WHERE is_deleted = FALSE";
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                     PreparedStatement selectManufacturerStatement =
                             connection.prepareStatement(selectManufacturerSql)) {
            ResultSet resultSet = selectManufacturerStatement.executeQuery();
            while (resultSet.next()) {
                Manufacturer manufacturer = getManufacturerFromRS(resultSet);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturer from DB", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerSql =
                "UPDATE manufacturers SET name = ?, country = ? WHERE id= ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                        PreparedStatement updateManufacturerStatement =
                        connection.prepareStatement(updateManufacturerSql)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update manufacturer " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufacturerSql = "UPDATE manufacturers SET is_deleted = true where id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                        PreparedStatement deleteManufacturerStatement =
                        connection.prepareStatement(deleteManufacturerSql)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete manufacturer from DB by ID: " + id, e);
        }
    }

    private Manufacturer getManufacturerFromRS(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(id);
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        return manufacturer;
    }
}
