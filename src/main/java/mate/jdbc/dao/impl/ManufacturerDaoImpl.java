package mate.jdbc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement =
                        connection.prepareStatement(
                             Query.CREATE.getQuery(), Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                manufacturer.setId(generatedKeys.getObject(1, Long.class));
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer to DB", e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement =
                        connection.prepareStatement(Query.GET_BY_ID.getQuery())) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            if (resultSet != null && resultSet.next()) {
                return Optional.of(createManufacturer(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by id", e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturerList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllManufacturersStatement = connection.createStatement()) {
            ResultSet resultSet =
                    getAllManufacturersStatement.executeQuery(Query.SELECT.getQuery());
            while (resultSet.next()) {
                manufacturerList.add(createManufacturer(resultSet));
            }
            return manufacturerList;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufactures from DB", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement =
                        connection.prepareStatement(Query.UPDATE_BY_ID.getQuery())) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            if (updateManufacturerStatement.executeUpdate() > 0) {
                return manufacturer;
            }
            throw new NoSuchElementException(String.format(
                    "Manufacturer %s doesn't exist in DB", manufacturer.toString()));
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer by id", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateFormatStatement =
                        connection.prepareStatement(Query.DELETE.getQuery())) {
            updateFormatStatement.setLong(1, id);
            return updateFormatStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer in DB", e);
        }
    }

    private Manufacturer createManufacturer(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getObject("id", Long.class));
        manufacturer.setName(resultSet.getString("name"));
        manufacturer.setCountry(resultSet.getString("country"));
        return manufacturer;
    }

    private enum Query {
        DELETE("UPDATE manufacturers SET is_deleted = true WHERE id = ?;"),
        SELECT("SELECT * FROM manufacturers WHERE is_deleted = false;"),
        CREATE("INSERT INTO manufacturers (name,country) VALUES(?,?);"),
        UPDATE_BY_ID("UPDATE manufacturers SET name = ?, "
                + "country = ? WHERE id = ? AND is_deleted = false;"),
        GET_BY_ID("SELECT * FROM manufacturers WHERE is_deleted = false AND id = ?;");

        private String query;

        Query(String query) {
            this.query = query;
        }

        public String getQuery() {
            return query;
        }
    }
}
