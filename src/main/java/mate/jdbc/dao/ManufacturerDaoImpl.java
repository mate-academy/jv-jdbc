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
import mate.jdbc.util.ConnectionsUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override

    public Manufacturer create(Manufacturer manufacturer) {
        String selectQuery = "SELECT id FROM manufacturers WHERE name = ? AND country = ?";
        String insertQuery = "INSERT INTO manufacturers (name, country) VALUES (?, ?)";

        try (Connection connection = ConnectionsUtil.getConnection();
                PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
                PreparedStatement insertStatement = connection
                        .prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            selectStatement.setString(1, manufacturer.getName());
            selectStatement.setString(2, manufacturer.getCountry());
            ResultSet selectResultSet = selectStatement.executeQuery();

            if (selectResultSet.next()) {
                manufacturer.setId(selectResultSet.getLong("id"));
                return manufacturer;
            } else {
                insertStatement.setString(1, manufacturer.getName());
                insertStatement.setString(2, manufacturer.getCountry());
                insertStatement.executeUpdate();
                ResultSet insertResultSet = insertStatement.getGeneratedKeys();

                if (insertResultSet.next()) {
                    manufacturer.setId(insertResultSet.getLong(1));
                }
                return manufacturer;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't create manufacturer. " + manufacturer, e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String query = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionsUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Manufacturer manufacturer = null;
            if (resultSet.next()) {
                manufacturer = getManufacturer(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get manufacturer by id " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String query = "SELECT * FROM manufacturers WHERE is_deleted = FALSE";
        List<Manufacturer> allManufacturer = new ArrayList<>();
        try (Connection connection = ConnectionsUtil.getConnection();
                Statement getAllManufacturerStatements = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturerStatements
                    .executeQuery(query);
            while (resultSet.next()) {
                allManufacturer.add(getManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get a list of manufacturers "
                    + "from manufacturers table.", e);
        }
        return allManufacturer;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String query = "UPDATE manufacturers SET name = ?, country = ? "
                + "WHERE id = ? AND is_deleted = FALSE ";
        try (Connection connection = ConnectionsUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.setLong(3, manufacturer.getId());
            statement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't update a manufacturer "
                    + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ?; ";
        try (Connection connection = ConnectionsUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't delete a manufacturer by id " + id, e);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        return new Manufacturer(id, name, country);
    }
}
