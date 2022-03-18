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
    private static final int ID_INDEX = 1;
    private static final int NAME_INDEX = 2;
    private static final int COUNTRY_INDEX = 3;

    @Override
    public Manufacturer add(Manufacturer manufacturer) {
        if (manufacturer.getName() != null & manufacturer.getCountry() != null) {
            try (Connection connection = ConnectionUtil.getConnection()) {
                String insertQuery = "INSERT INTO manufacturer "
                        + "(name,country) values(?,?)";
                PreparedStatement createStatement =
                        connection.prepareStatement(insertQuery,
                                Statement.RETURN_GENERATED_KEYS);
                createStatement.setString(1, manufacturer.getName());
                createStatement.setString(2, manufacturer.getCountry());
                createStatement.executeUpdate();
                ResultSet generatedKeys = createStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    Long id = generatedKeys.getObject(1, Long.class);
                    manufacturer.setId(id);
                }
            } catch (SQLException e) {
                throw new DataProcessingException("Can`t create new manufacturer", e);
            }
        } else {
            throw new RuntimeException("Ca`nt create manufacturer with this parameters");
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String getManufacturerQuery = "SELECT * FROM manufacturers WHERE id = ?"
                    + " AND is_deleted = FALSE;";
            PreparedStatement preparedStatement =
                    connection.prepareStatement(getManufacturerQuery);
            preparedStatement.setLong(ID_INDEX, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getManufacturerFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Ca`nt get manufacturer with this id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllQuery = "SELECT * FROM manufacturer WHERE is_deleted = FALSE";
        List<Manufacturer> listOfManufacture;
        try (Connection connection = ConnectionUtil.getConnection()) {
            listOfManufacture = new ArrayList<>();
            Statement getAllStatement = connection.createStatement();
            ResultSet resultSet = getAllStatement.executeQuery(getAllQuery);
            while (resultSet.next()) {
                Manufacturer manufacturer = getManufacturerFromResultSet(resultSet);
                listOfManufacture.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Ca`nt get list manufacturers from DB ", e);
        }
        return listOfManufacture;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String updateQuery = "UPDATE manufacturers SET name = ?, country = ? where id = ?"
                    + " and is_deleted = false;";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update DB with this manufacturer ", e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteQuery = "UPDATE manufacturer SET is_deleted = 1 " + "WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setLong(ID_INDEX, id);
            return preparedStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Ca`nt delete manufacturer with this id: " + id, e);
        }
    }

    private Manufacturer getManufacturerFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject(ID_INDEX, Long.class);
        String name = resultSet.getString(NAME_INDEX);
        String country = resultSet.getString(COUNTRY_INDEX);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(id);
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        return manufacturer;
    }
}
