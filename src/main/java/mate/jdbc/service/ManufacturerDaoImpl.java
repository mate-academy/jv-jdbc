package mate.jdbc.service;

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
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final int PARAMETER_INDEX_OF_NAME_ID = 1;
    private static final int PARAMETER_INDEX_OF_COUNTRY = 2;
    private static final int PARAMETER_INDEX_OF_ID = 3;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerQuery =
                "INSERT INTO manufacturers(name, country) VALUES(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement =
                        connection.prepareStatement(insertManufacturerQuery,
                             Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(PARAMETER_INDEX_OF_NAME_ID,
                    manufacturer.getName());
            createManufacturerStatement.setString(PARAMETER_INDEX_OF_COUNTRY,
                    manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKey = createManufacturerStatement.getGeneratedKeys();
            if (generatedKey.next()) {
                Long id = generatedKey.getObject(PARAMETER_INDEX_OF_NAME_ID, Long.class);
                manufacturer.setId(id);
            }
            return manufacturer;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't insert manufacturer "
                    + "to DataBase " + manufacturer, ex);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturerQuery = "SELECT * FROM manufacturers WHERE id = ? "
                + "AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement =
                        connection.prepareStatement(getManufacturerQuery)) {
            getManufacturerStatement.setLong(PARAMETER_INDEX_OF_NAME_ID, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            Manufacturer manufacturer = null;
            if (resultSet.next()) {
                manufacturer = createManufacturerFromResultSet(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't get manufacturer "
                    + "from DataBase by id = " + id, ex);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String manufacturerQuery = "SELECT * FROM manufacturers WHERE is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturerStatement =
                        connection.prepareStatement(manufacturerQuery)) {
            ResultSet resultSet = getAllManufacturerStatement.executeQuery();
            while (resultSet.next()) {
                manufacturers.add(createManufacturerFromResultSet(resultSet));
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't get manufacturers from DataBase ",ex);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerQuery = "UPDATE manufacturers SET name = ?, country = ?"
                + "WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement =
                           connection.prepareStatement(updateManufacturerQuery)) {
            updateManufacturerStatement.setString(PARAMETER_INDEX_OF_NAME_ID,
                    manufacturer.getName());
            updateManufacturerStatement.setString(PARAMETER_INDEX_OF_COUNTRY,
                    manufacturer.getCountry());
            updateManufacturerStatement.setLong(PARAMETER_INDEX_OF_ID,
                    manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't update manufacturer "
                    + "to DataBase " + manufacturer, ex);
        }
    }

    @Override
    public boolean delete(Long id) {
        String manufacturerToDeleteQuery =
                "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement =
                        connection.prepareStatement(manufacturerToDeleteQuery)) {
            deleteManufacturerStatement.setLong(PARAMETER_INDEX_OF_NAME_ID, id);
            return deleteManufacturerStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't delete manufacturer from DB " + id, ex);
        }
    }

    private Manufacturer createManufacturerFromResultSet(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        Long id = resultSet.getObject("id", Long.class);
        return new Manufacturer(id, name, country);
    }
}
