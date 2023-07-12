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
import mate.jdbc.util.DataBaseConnector;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try (Connection connection = DataBaseConnector.getConnection(); PreparedStatement
                statement = connection.prepareStatement((Query.INSERT.string),
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            } else {
                throw new DataProcessingException("Insert error!", new Throwable());
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create manufacturer!", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        try (Connection connection = DataBaseConnector.getConnection(); PreparedStatement statement
                = connection.prepareStatement(Query.SELECT_ALL.string + " AND id = " + id)) {
            ResultSet resultSet = statement
                    .executeQuery();
            if (resultSet.next()) {
                return Optional.of(getManufacturerFromResultSet(resultSet));
            }
            throw new DataProcessingException("Can't find manufacturer!", new Throwable());
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find manufacturer!", e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        try (Connection connection = DataBaseConnector.getConnection(); PreparedStatement
                preparedStatement = connection.prepareStatement(Query.SELECT_ALL.string)) {
            List<Manufacturer> manufacturerList = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                manufacturerList.add(getManufacturerFromResultSet(resultSet));
            }
            return manufacturerList;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturers from DB!", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        try (Connection connection = DataBaseConnector.getConnection(); PreparedStatement
                statement = connection.prepareStatement(Query.UPDATE.string)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.setLong(3, manufacturer.getId());
            statement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer!", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = DataBaseConnector.getConnection(); PreparedStatement
                preparedStatement = connection.prepareStatement(Query.DELETE.string)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Manufacturer getManufacturerFromResultSet(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getObject(1, Long.class));
        manufacturer.setName(resultSet.getString(2));
        manufacturer.setCountry(resultSet.getString(3));
        return manufacturer;
    }

    private enum Query {
        SELECT_ALL("SELECT * FROM manufacturers WHERE is_deleted = FALSE"),
        INSERT("INSERT INTO manufacturers (name, country) VALUES (?, ?);"),
        UPDATE("UPDATE manufacturers SET name = ?, country = ? "
                + "WHERE id = ? AND is_deleted = FALSE;"),
        DELETE("UPDATE manufacturers SET is_deleted = TRUE WHERE id = ?");

        private String string;

        Query(String string) {
            this.string = string;
        }
    }
}

