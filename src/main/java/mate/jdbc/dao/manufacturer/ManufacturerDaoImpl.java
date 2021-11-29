package mate.jdbc.dao.manufacturer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import mate.jdbc.exceptions.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.models.db.models.Manufacturer;
import mate.jdbc.services.connection.Connector;
import mate.jdbc.services.parsers.ManufacturerParser;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final String TABLE_NAME = "manufactures";
    private Connector connector;

    @Override
    public Manufacturer create(Manufacturer dbModel) {
        String query = String.format("INSERT INTO `%s` "
                + "(`name`, `country`) VALUES (?, ?);", TABLE_NAME);
        try (Connection connection = connector.connect();
                PreparedStatement statement
                        = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, dbModel.getName());
            statement.setString(2, dbModel.getCountry());
            statement.executeUpdate();
            ResultSet generatedKey = statement.getGeneratedKeys();
            if (generatedKey.next()) {
                Long id = generatedKey.getObject(1, Long.class);
                dbModel.setId(id);
            }
        } catch (SQLException e) {
            String errorMessage = "Can't create model: " + dbModel;
            throw new DataProcessingException(errorMessage, e);
        }
        return dbModel;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getQuery = String.format(
                "SELECT * FROM %s "
                        + "WHERE id = ?;", TABLE_NAME);
        try (Connection connection = connector.connect();
                PreparedStatement statement = connection.prepareStatement(getQuery)) {
            statement.setString(1, String.valueOf(id));
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return Optional.ofNullable(new ManufacturerParser().parseRow(resultSet));
        } catch (SQLException e) {
            String errorMessage = "Can't get model by id: " + id;
            throw new DataProcessingException(errorMessage, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String query = String.format(
                "SELECT * FROM %s "
                + "WHERE is_deleted = 0;", TABLE_NAME);
        try (Connection connection = connector.connect();
                Statement statement = connection.createStatement()) {
            return new ManufacturerParser().parse(statement.executeQuery(query));
        } catch (SQLException e) {
            String errorMessage = "Can't get all data from '" + TABLE_NAME + "' table.";
            throw new DataProcessingException(errorMessage, e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer dbModel) {
        String deleteQuery = String.format(
                "UPDATE %s SET name = ?, country = ? "
                + "WHERE id = ?;", TABLE_NAME);
        try (Connection connection = connector.connect();
                PreparedStatement softDeleteStatement = connection.prepareStatement(deleteQuery)) {
            softDeleteStatement.setString(1, dbModel.getName());
            softDeleteStatement.setString(2, dbModel.getCountry());
            softDeleteStatement.setString(3, String.valueOf(dbModel.getId()));
            softDeleteStatement.executeUpdate();
            return dbModel;
        } catch (SQLException e) {
            String errorMessage = "Can't update row by model: " + dbModel;
            throw new DataProcessingException(errorMessage, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteQuery = String.format(
                "UPDATE %s SET is_deleted = 1 "
                        + "WHERE id = ?;", TABLE_NAME);
        try (Connection connection = connector.connect();
                PreparedStatement softDeleteStatement = connection.prepareStatement(deleteQuery)) {
            softDeleteStatement.setString(1, String.valueOf(id));
            int updatedRows = softDeleteStatement.executeUpdate();
            return updatedRows > 0;
        } catch (SQLException e) {
            String errorMessage = "Can't delete row by id: " + id;
            throw new DataProcessingException(errorMessage, e);
        }
    }
}
