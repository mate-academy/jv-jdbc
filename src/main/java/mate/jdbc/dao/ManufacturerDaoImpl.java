package mate.jdbc.dao;

import static mate.jdbc.util.SqlQueries.DELETE_MANUFACTURER_BY_ID;
import static mate.jdbc.util.SqlQueries.INSERT_MANUFACTURER;
import static mate.jdbc.util.SqlQueries.SELECT_ALL_MANUFACTURERS;
import static mate.jdbc.util.SqlQueries.SELECT_MANUFACTURER_BY_ID;
import static mate.jdbc.util.SqlQueries.UPDATE_MANUFACTURER;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private final Connection connection = ConnectionUtil.getConnection();

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
        try (PreparedStatement getManufacturer = connection.prepareStatement(
                SELECT_MANUFACTURER_BY_ID)) {
            getManufacturer.setObject(1, id);
            ResultSet resultSet = getManufacturer.executeQuery();
            Manufacturer manufacturer = new Manufacturer();
            if (resultSet.next()) {
                manufacturer.setId((Long) resultSet.getObject(1));
                manufacturer.setName(resultSet.getString(2));
                manufacturer.setCountry(resultSet.getString(3));
                return Optional.of(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB. ", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        try (PreparedStatement getManufacturers = connection.prepareStatement(
                SELECT_ALL_MANUFACTURERS)) {
            ResultSet resultSet = getManufacturers.executeQuery();
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
            preparedStatement.setObject(3, manufacturer.getId());
            int result = preparedStatement.executeUpdate();
            if (result >= 1) {
                return manufacturer;
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer with id = "
                    + manufacturer.getId()
                    + " in manufacturers table. ", e);
        }
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
