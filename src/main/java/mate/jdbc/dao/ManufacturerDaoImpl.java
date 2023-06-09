package mate.jdbc.dao;

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
    private static final String GET_ALL = "SELECT * FROM manufacturers WHERE is_deleted = FALSE;";
    private static final String GET_BY_ID
            = "SELECT * FROM manufacturers WHERE is_deleted = FALSE AND id = ?;";
    private static final String UPDATE = "UPDATE manufacturers SET name = ?,"
            + " country = ? WHERE is_deleted = FALSE AND id = ?;";
    private static final String DELETE_BY_ID
            = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
    private static final String CREATE = "INSERT INTO manufacturers(name, country) VALUES(?, ?);";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try (PreparedStatement createStatement = ConnectionUtil
                .getConnection().prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
            createStatement.setString(1, manufacturer.getName());
            createStatement.setString(2, manufacturer.getCountry());
            createStatement.executeUpdate();
            ResultSet generatedKeys = createStatement.getGeneratedKeys();
            while (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get manufacturer by id ", e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        try (PreparedStatement getManufacturerStatement = ConnectionUtil
                .getConnection().prepareStatement(GET_BY_ID)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            return resultSet.next() ? Optional.of(manufacturerParser(resultSet))
                    : Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get manufacturer by id " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (PreparedStatement getAllManufacturersStatement = ConnectionUtil
                .getConnection().prepareStatement(GET_ALL)) {
            ResultSet resultSet = getAllManufacturersStatement.executeQuery();
            while (resultSet.next()) {
                allManufacturers.add(manufacturerParser(resultSet));
            }
            return allManufacturers;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all manufacturers from DB", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        try (PreparedStatement updateStatement = ConnectionUtil
                .getConnection().prepareStatement(UPDATE)) {
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.setLong(3, manufacturer.getId());
            updateStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update DB!", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (PreparedStatement deleteStatement = ConnectionUtil
                .getConnection().prepareStatement(DELETE_BY_ID)) {
            deleteStatement.setLong(1, id);
            return deleteStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete manufacturer by id " + id, e);
        }
    }

    private Manufacturer manufacturerParser(ResultSet resultSet) throws SQLException {
        return new Manufacturer(resultSet.getObject(1, Long.class),
                resultSet.getString("name"), resultSet.getString("country"));
    }
}
