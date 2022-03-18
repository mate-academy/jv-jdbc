package mate.jdbc.dao;

import mate.jdbc.exeptions.DataProcessingException;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ManufacturerDaoImpl implements ManufacturerDao{
    private static final int INDEX_ID = 1;
    private static final int INDEX_NAME = 2;
    private static final int INDEX_COUNTRY = 3;

    @Override
    public Manufacturer add(Manufacturer manufacturer) {
        String insertManufacturerRequest = "INSERT INTO manufacturer(id) values(?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement createManufacturerElement = connection.prepareStatement(insertManufacturerRequest,
                    Statement.RETURN_GENERATED_KEYS);
            createManufacturerElement.executeUpdate(insertManufacturerRequest);
            ResultSet generatedKeys = createManufacturerElement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(INDEX_ID, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t create connection!!", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Manufacturer manufacturer;
        String getManufacturerRequest = "SELECT id, name, country FROM manufacturers"
                + " where id = ? and is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection()){
            PreparedStatement getManufacturerStatement = connection.prepareStatement(getManufacturerRequest);
            {
                getManufacturerStatement.setLong(INDEX_ID, id);
                ResultSet resultSet = getManufacturerStatement.executeQuery();
                if (resultSet.next()) {
                    return Optional.of(getManufacturerFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t create connection!!", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllRequest = "SELECT * FROM manufacturers where is_deleted = false";
        List<Manufacturer> allManufacturer = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement getAllManufacturersStatement = connection.prepareStatement(getAllRequest);
            ResultSet resultSet = getAllManufacturersStatement.executeQuery();
            while (resultSet.next()) {
                Manufacturer manufacturer = getManufacturerFromResultSet(resultSet);
                allManufacturer.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t create connection!!", e);
        }
        return allManufacturer;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET name = ?, country = ? where id = ?"
                + " and is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement updateManufactureStatement  = connection.prepareStatement(updateRequest);
            updateManufactureStatement.setLong(INDEX_ID, manufacturer.getId());
            updateManufactureStatement.setString(INDEX_NAME, manufacturer.getName());
            updateManufactureStatement.setString(INDEX_COUNTRY, manufacturer.getCountry());
            updateManufactureStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer in DB,"
                    + " manufacturer: " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequestStatement = "UPDATE manufacturers SET is_deleted = true where id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement deleteManufacturerStatement =
                     connection.prepareStatement(deleteRequestStatement)) {
            deleteManufacturerStatement.setLong(INDEX_ID, id);
            return deleteManufacturerStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer from DB by id: " + id, e);
        }
    }

    private Manufacturer getManufacturerFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        return new Manufacturer(id, name, country);
    }
}
