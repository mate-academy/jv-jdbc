package mate.jdbc.dao;

import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static mate.jdbc.util.SqlQueries.*;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    Connection connection = ConnectionUtil.getConnection();
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try (PreparedStatement createManufacturerStatement = connection.prepareStatement(
                INSERT_MANUFACTURER, PreparedStatement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer to DB. ", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        try (Statement getManufacturers = connection.createStatement()) {
            ResultSet resultSet = getManufacturers.executeQuery(SELECT_ALL_MANUFACTURERS);
            return extractManufacturers(resultSet);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB. ", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                UPDATE_MANUFACTURER)) {
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            int result = preparedStatement.executeUpdate();
            if (result >= 1) {
                return manufacturer;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer with name = "
                    + manufacturer.getName()
                    + " and country = "
                    + manufacturer.getCountry()
                    + " in manufacturers table. ", e);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                DELETE_MANUFACTURER_BY_ID)) {
            preparedStatement.setObject(1, id);
            return preparedStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer by id = "
                    + id + " from manufacturers table. ", e);
        }
    }

    private List<Manufacturer> extractManufacturers(ResultSet resultSet) throws SQLException {
        List<Manufacturer> manufacturersList = new ArrayList<>();
        while (resultSet.next()) {
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setId(resultSet.getObject(1, Long.class));
            manufacturer.setName(resultSet.getString(2));
            manufacturer.setCountry(resultSet.getString(3));
            manufacturersList.add(manufacturer);
        }
        return manufacturersList;
    }
}
