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
import mate.jdbc.util.DataProcessingException;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String sqlRequest = "INSERT INTO manufacturers "
                + "(manufacturers_name, manufacturers_country) VALUES (?, ?);";
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement manufacturerCreateStatement =
                        connection.prepareStatement(sqlRequest, Statement.RETURN_GENERATED_KEYS)
        ) {
            manufacturerCreateStatement.setString(1, manufacturer.getName());
            manufacturerCreateStatement.setString(2, manufacturer.getCountry());
            manufacturerCreateStatement.executeUpdate();
            ResultSet resultSetUpdate = manufacturerCreateStatement.getGeneratedKeys();
            while (resultSetUpdate.next()) {
                Long id = resultSetUpdate.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't creat manufacturer " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Manufacturer manufacturer = null;
        String sqlRequest = "SELECT manufacturers_id, manufacturers_name, manufacturers_country "
                + "FROM manufacturers WHERE manufacturers_id = ? AND is_deleted = false;";
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement manufacturersGetStatement =
                        connection.prepareStatement(sqlRequest)
        ) {
            manufacturersGetStatement.setObject(1, id);
            manufacturersGetStatement.executeQuery();
            ResultSet resultSetById = manufacturersGetStatement.getResultSet();
            while (resultSetById.next()) {
                Long manufacturerId = resultSetById.getObject(1, Long.class);
                String name = resultSetById.getString(2);
                String country = resultSetById.getString(3);
                manufacturer = new Manufacturer(manufacturerId, name, country);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by id, with id:" + id, e);
        }
        return Optional.ofNullable(manufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturerList = new ArrayList<>();
        String sqlRequest = "SELECT manufacturers_id, manufacturers_name, manufacturers_country "
                + "FROM manufacturers WHERE is_deleted = false;";
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement manufacturersGetAllStatement =
                        connection.prepareStatement(sqlRequest)
        ) {
            manufacturersGetAllStatement.executeQuery();
            ResultSet resultSetFromDb = manufacturersGetAllStatement.getResultSet();
            while (resultSetFromDb.next()) {
                Long id = resultSetFromDb.getObject(1, Long.class);
                String name = resultSetFromDb.getString(2);
                String country = resultSetFromDb.getString(3);
                Manufacturer manufacturer = new Manufacturer(id, name, country);
                manufacturerList.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturer", e);
        }
        return manufacturerList;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String sqlRequest = "UPDATE manufacturers "
                + "SET manufacturers_name = ?, manufacturers_country = ? "
                + "WHERE manufacturers_id = ?;";
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement manufacturerUpdateStatement =
                        connection.prepareStatement(sqlRequest)
        ) {
            manufacturerUpdateStatement.setString(1, manufacturer.getName());
            manufacturerUpdateStatement.setString(2, manufacturer.getCountry());
            manufacturerUpdateStatement.setObject(3, manufacturer.getId());
            manufacturerUpdateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't do the update " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String sqlRequest = "UPDATE manufacturers "
                + "SET is_deleted = true WHERE manufacturers_id = ?;";
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement manufacturerDeleteStatement =
                        connection.prepareStatement(sqlRequest)
        ) {
            manufacturerDeleteStatement.setObject(1, id);
            return manufacturerDeleteStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer with id: " + id, e);
        }
    }
}
