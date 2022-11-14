package mate.jdbc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.exeptions.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final int ID = 1;
    private static final int NAME = 2;
    private static final int COUNTRY = 3;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertRequest = "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement createStatement
                         = connection.prepareStatement(insertRequest,
                             Statement.RETURN_GENERATED_KEYS)) {
            createStatement.setString(1, manufacturer.getName());
            createStatement.setString(2, manufacturer.getCountry());
            createStatement.executeUpdate();
            ResultSet generatedKeys = createStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                manufacturer.setId(generatedKeys.getObject(ID, Long.class));
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add " + manufacturer + " to DB", e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getRequest = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement
                         = connection.prepareStatement(getRequest)) {
            updateStatement.setLong(1, id);
            ResultSet resultSet = updateStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer from DB " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllRequest = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        List<Manufacturer> manufacturerList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getAllStatement
                         = connection.prepareStatement(getAllRequest)) {
            ResultSet resultSet = getAllStatement.executeQuery();
            while (resultSet.next()) {
                manufacturerList.add(getManufacturer(resultSet));
            }
            return manufacturerList;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get data from DB", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET NAME = ?, country = ? "
                + "WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement updateStatement
                         = connection.prepareStatement(updateRequest)) {
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.setLong(3, manufacturer.getId());
            updateStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer " + manufacturer
                    + " data to DB", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement deleteStatement
                        = connection.prepareStatement(deleteRequest)) {
            deleteStatement.setLong(ID, id);
            return deleteStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete from DB " + id, e);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getObject(ID, Long.class));
        manufacturer.setName(resultSet.getString(NAME));
        manufacturer.setCountry(resultSet.getString(COUNTRY));
        return manufacturer;
    }
}
