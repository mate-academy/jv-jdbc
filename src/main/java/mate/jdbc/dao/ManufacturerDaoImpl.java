package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mate.jdbc.exception.CustomException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final String READ_SELECT
            = "SELECT * FROM manufacturers WHERE is_deleted = false;";
    private static final String CREATE_SELECT
            = "INSERT INTO manufacturers (name, country) values(?, ?);";
    private static final String DELETE_SELECT
            = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
    private static final String GET_ID_SELECT
            = "SELECT * FROM manufacturers WHERE is_deleted = FALSE AND id = (?);";
    private static final String UPDATE_SELECT
            = "UPDATE manufacturers SET name = ?, country = ? WHERE id = ? AND is_deleted = FALSE";

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturerList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             Statement getAllManufacturerStatement = connection.createStatement()
        ) {
            ResultSet resultSet = getAllManufacturerStatement
                    .executeQuery(READ_SELECT);
            while (resultSet.next()) {
                Long id = resultSet.getObject("id", Long.class);
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                boolean is_deleted = resultSet.getObject("is_deleted", Boolean.class);
                Manufacturer manufacturerFormat = new Manufacturer();
                manufacturerFormat.setId(id);
                manufacturerFormat.setName(name);
                manufacturerFormat.setCountry(country);
                manufacturerFormat.setDeleted(is_deleted);
                manufacturerList.add(manufacturerFormat);
            }
        } catch (SQLException e) {
            throw new CustomException("Can't get all formats from DB", e);
        }
        return manufacturerList;
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement createManufacturerStatement = connection
                     .prepareStatement(CREATE_SELECT, Statement.RETURN_GENERATED_KEYS)
        ) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new CustomException("Can't insert manufacturer to DB", e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement deleteManufacturerStatement = connection
                     .prepareStatement(DELETE_SELECT, Statement.RETURN_GENERATED_KEYS)
        ) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new CustomException("Can't insert format to DB", e);
        }
    }

    @Override
    public boolean updateManufacturer(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement updateManufacturerStatement =
                     connection.prepareStatement(UPDATE_SELECT)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
            return updateManufacturerStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new CustomException("Can't insert format to DB", e);
        }
    }

    @Override
    public Manufacturer getById(Long id) {
        Manufacturer manufacturer = new Manufacturer();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement getIdManufacturerStatement = connection
                     .prepareStatement(GET_ID_SELECT)
        ) {
            getIdManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getIdManufacturerStatement.executeQuery();
            while (resultSet.next()) {
                manufacturer = getResultToManufacturer(resultSet);
            }
        } catch (SQLException e) {
            throw new CustomException("Can't insert format to DB", e);
        }
        return manufacturer;
    }

    private Manufacturer getResultToManufacturer(ResultSet resultSet) throws SQLException {
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
