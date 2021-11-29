package mate.jdbc.dao.manufacturer;

import java.lang.reflect.Field;
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

    @Override
    public Manufacturer create(Manufacturer manufacture) {
        String query = "INSERT INTO manufactures "
                + "(`name`, `country`) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, manufacture.getName());
            statement.setString(2, manufacture.getCountry());
            statement.executeUpdate();
            ResultSet generatedKey = statement.getGeneratedKeys();
            if (generatedKey.next()) {
                Long id = generatedKey.getObject(1, Long.class);
                manufacture.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create model: " + manufacture, e);
        }
        return manufacture;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String query = "SELECT * FROM manufactures WHERE id = ? and is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(parseRow(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get model by id: " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String query = "SELECT * FROM manufactures WHERE is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            return parse(statement.executeQuery());
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all data from `manufactures` table.", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String query = "UPDATE manufactures SET name = ?, country = ? "
                + "WHERE id = ? and is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement softDeleteStatement = connection.prepareStatement(query)) {
            softDeleteStatement.setString(1, manufacturer.getName());
            softDeleteStatement.setString(2, manufacturer.getCountry());
            softDeleteStatement.setLong(3, manufacturer.getId());
            softDeleteStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update row by model: " + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE manufactures SET is_deleted = true "
                        + "WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement softDeleteStatement = connection.prepareStatement(query)) {
            softDeleteStatement.setLong(1, id);
            int updatedRows = softDeleteStatement.executeUpdate();
            return updatedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete row by id: " + id, e);
        }
    }

    private List<Manufacturer> parse(ResultSet resultSet) {
        List<Manufacturer> resultList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                resultList.add(parseRow(resultSet));
            }
            return resultList;
        } catch (SQLException e) {
            throw new RuntimeException("Cannot get asses to ResultSet.", e);
        }
    }

    private Manufacturer parseRow(ResultSet resultSet) {
        try {
            Manufacturer model = new Manufacturer();
            for (Field field : Manufacturer.class.getDeclaredFields()) {
                field.setAccessible(true);
                field.set(model, resultSet.getObject(field.getName(), field.getType()));
            }
            return model;
        } catch (SQLException e) {
            throw new RuntimeException("Can't convert resultSet to model.", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cant get asses to the field.", e);
        }
    }
}
