package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.ConnectionUtil;
import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final String TABLE_NAME = "manufacturer";
    private static final int ID_COLUMN_INDEX = 1;
    private static final int NAME_COLUMN_INDEX = 2;
    private static final int COUNTRY_COLUMN_INDEX = 3;
    private ConnectionUtil connectionUtil = new ConnectionUtil();

    public List<Manufacturer> gerAll() {
        List<Manufacturer> manufacturerList;
        try (Connection connection = connectionUtil.getConnection()) {
            String getAllQuery = "SELECT * FROM " + TABLE_NAME + " WHERE is_deleted = FALSE";
            manufacturerList = new ArrayList<>();
            Manufacturer manufacturer;
            Statement getAllStatement = connection.createStatement();
            ResultSet resultSet = getAllStatement.executeQuery(getAllQuery);
            while (resultSet.next()) {
                manufacturer = getManufacturer(resultSet);
                manufacturerList.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Ca`nt get list manufacturers from DB ", e);
        }
        return manufacturerList;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        try (Connection connection = connectionUtil.getConnection()) {
            String getManufacturerQuery = "SELECT * FROM manufacturers WHERE id = ?"
                    + " AND is_deleted = FALSE;";
            PreparedStatement preparedStatement =
                    connection.prepareStatement(getManufacturerQuery);
            preparedStatement.setLong(ID_COLUMN_INDEX, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Ca`nt get manufacturer with this id: " + id, e);
        }
        return Optional.empty();
    }

    public boolean delete(Long id) {
        try (Connection connection = connectionUtil.getConnection()) {
            String deleteQuery = "UPDATE " + TABLE_NAME + " SET is_deleted = 1 " + "WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setLong(ID_COLUMN_INDEX, id);
            return preparedStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Ca`nt delete manufacturer with this id: " + id, e);
        }
    }

    public Manufacturer update(Manufacturer manufacturer) {
        try (Connection connection = connectionUtil.getConnection()) {
            String country = manufacturer.getCountry();
            String name = manufacturer.getName();
            String updateQuery = "UPDATE " + TABLE_NAME + " SET name = '"
                    + name + "'," + " country = '"
                    + country + "' WHERE id = " + manufacturer.getId();
            PreparedStatement preparedStatement
                    = connection.prepareStatement(updateQuery, Statement.RETURN_GENERATED_KEYS);
            return preparedStatement.executeUpdate() >= 1 ? manufacturer : null;
        } catch (SQLException e) {
            throw new DataProcessingException("Ca`nt update DB with this manufacturer ", e);
        }
    }

    public Manufacturer create(Manufacturer manufacturer) {
        if (manufacturer.getName() != null & manufacturer.getCountry() != null) {
            try (Connection connection = connectionUtil.getConnection()) {
                String insertQuery = "INSERT INTO " + TABLE_NAME
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
                throw new DataProcessingException("Ca`nt create new manufacturer", e);
            }
        } else {
            throw new RuntimeException("Ca`nt create manufacturer with this parameters");
        }
        return manufacturer;
    }

    private Manufacturer getManufacturer(ResultSet resultSet) {
        Long id;
        String name;
        String country;
        try {
            id = resultSet.getObject(ID_COLUMN_INDEX, Long.class);
            name = resultSet.getString(NAME_COLUMN_INDEX);
            country = resultSet.getString(COUNTRY_COLUMN_INDEX);
        } catch (SQLException e) {
            throw new DataProcessingException("Ca`nt get manufacturer", e);
        }
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(id);
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        return manufacturer;
    }
}
