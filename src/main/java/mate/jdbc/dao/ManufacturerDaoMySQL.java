package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.lib.Dao;
import mate.jdbc.lib.DataProcessingException;
import mate.jdbc.model.Manufacturer;

@Dao
public class ManufacturerDaoMySQL implements ManufacturerDao {
    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/taxi_service";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root";
    private static final String TABLE_NAME = "manufacturers";
    private static final Connection daoConnection;

    static {
        try {
            Class.forName(DRIVER_CLASS);
            daoConnection = DriverManager.getConnection(CONNECTION_STRING, DB_USER, DB_PASS);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't connect to '" + "' " + CONNECTION_STRING, e);
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can't load class '" + "' " + DRIVER_CLASS, e);
        }
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String createSQL = "INSERT INTO " + TABLE_NAME + "(name, country) VALUES(?, ?)";
        try (PreparedStatement createStatement = daoConnection.prepareStatement(createSQL,
                    Statement.RETURN_GENERATED_KEYS)) {
            createStatement.setString(1, manufacturer.getName());
            createStatement.setString(2, manufacturer.getCountry());
            createStatement.executeUpdate();
            ResultSet genIdSet = createStatement.getGeneratedKeys();
            if (genIdSet.next()) {
                manufacturer.setId(genIdSet.getObject(1, Long.class));
            }
            genIdSet.close();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't execute '" + createSQL + "'", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE (id = ?) AND (is_deleted = 0)";
        try (PreparedStatement selectStatement = daoConnection.prepareStatement(selectSQL)) {
            selectStatement.setObject(1, id);
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(resultSet.getObject("id", Long.class));
                manufacturer.setName(resultSet.getString("name"));
                manufacturer.setCountry(resultSet.getString("country"));
                return Optional.of(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't execute '" + selectSQL + "'", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE is_deleted = 0";
        List<Manufacturer> manufacturerList = new ArrayList<>();
        try (PreparedStatement selectStatement = daoConnection.prepareStatement(selectSQL)) {
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(resultSet.getObject("id", Long.class));
                manufacturer.setName(resultSet.getString("name"));
                manufacturer.setCountry(resultSet.getString("country"));
                manufacturerList.add(manufacturer);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't execute '" + selectSQL + "'", e);
        }
        return manufacturerList;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateSQL = "UPDATE " + TABLE_NAME + " SET name = ?, country = ? WHERE id = ?";
        try (PreparedStatement updateStatement = daoConnection.prepareStatement(updateSQL)) {
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.setObject(3, manufacturer.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't execute '" + updateSQL + "'", e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String updateSQL = "UPDATE " + TABLE_NAME + " SET is_deleted = 1 WHERE id = ?";
        try (PreparedStatement updateStatement = daoConnection.prepareStatement(updateSQL)) {
            updateStatement.setObject(1, id);
            return updateStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't execute '" + updateSQL + "'", e);
        }
    }
}
