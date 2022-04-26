package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.exeptions.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final String CREATE_QUERY
            = "INSERT INTO manufacturers(name, country) VALUES (?, ?);";
    private static final String GET_QUERY
            = "SELECT * FROM manufacturers WHERE id=(?) and is_deleted=FALSE;";
    private static final String GET_ALL_QUERY
            = "SELECT * FROM  manufacturers WHERE is_deleted=FALSE;";
    private static final String UPDATE_QUERY
            = "UPDATE manufacturers SET name=(?), country=(?) WHERE id=(?) and is_deleted=FALSE;";
    private static final String DELETE_QUERY
            = "UPDATE manufacturers SET is_deleted=true WHERE id=(?);";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement creationStatement = connection
                        .prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            creationStatement.setString(1, manufacturer.getName());
            creationStatement.setString(2, manufacturer.getCountry());
            creationStatement.executeUpdate();
            ResultSet generatedKeys = creationStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't crate manufacturer " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getStatement
                        = connection.prepareStatement(GET_QUERY)) {
            getStatement.setLong(1, id);
            ResultSet resultSet = getStatement.executeQuery();
            return getManufacturerOptional(id, resultSet);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get by id" + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllStatement
                        = connection.prepareStatement(GET_ALL_QUERY)) {
            ResultSet resultSet = getAllStatement.executeQuery();
            return getManufacturerList(resultSet);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all using " + GET_ALL_QUERY, e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement
                        = connection.prepareStatement(UPDATE_QUERY)) {
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.setLong(3, manufacturer.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteStatement
                        = connection.prepareStatement(DELETE_QUERY)) {
            deleteStatement.setLong(1, id);
            int updatedRows = deleteStatement.executeUpdate();
            return updatedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete by id " + id, e);
        }
    }

    private List<Manufacturer> getManufacturerList(ResultSet resultSet) throws SQLException {
        List<Manufacturer> manufacturers = new ArrayList<>();
        while (resultSet.next()) {
            manufacturers.add(parseManufacturerFromResultSet(resultSet));
        }
        return manufacturers;
    }

    private Optional<Manufacturer> getManufacturerOptional(Long id, ResultSet resultSet)
            throws SQLException {
        if (resultSet.next()) {
            return Optional.of(parseManufacturerFromResultSet(resultSet));
        }
        return Optional.empty();
    }

    private Manufacturer parseManufacturerFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject(1, Long.class);
        String name = resultSet.getObject(2, String.class);
        String country = resultSet.getObject(3, String.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(id);
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        return manufacturer;
    }

}
