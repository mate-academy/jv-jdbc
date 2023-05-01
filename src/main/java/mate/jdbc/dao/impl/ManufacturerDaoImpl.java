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
import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.utils.ConnectionUtils;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final String GET_MANUFACTURER_BY_ID_EXCEPTION =
            "Can`t get Manufacturer by id - %s";
    private static final String GET_ALL_MANUFACTURERS_EXCEPTION =
            "Can`t get all Manufacturers";
    private static final String CREATE_MANUFACTURERS_EXCEPTION =
            "Can`t create Manufacturer - %s";
    private static final String UPDATE_MANUFACTURERS_EXCEPTION =
            "Can`t create Manufacturer - %s";
    private static final String DELETE_BY_ID_MANUFACTURERS_EXCEPTION =
            "Can`t delete Manufacturer by id - %s?";
    private static final String GET_BY_ID_MANUFACTURERS_QUERY =
            "SELECT * FROM manufacturers WHERE is_deleted = false and id = ?";
    private static final String GET_ALL_MANUFACTURERS_QUERY =
            "SELECT * FROM manufacturers WHERE is_deleted = false";
    private static final String CREATE_MANUFACTURERS_QUERY =
            "INSERT INTO manufacturers(name, country) values(?, ?)";
    private static final String UPDATE_MANUFACTURERS_QUERY =
            "UPDATE manufacturers SET name = ?, country = ? WHERE id = ?";
    private static final String DELETE_BY_ID_MANUFACTURERS_QUERY =
            "UPDATE manufacturers SET is_deleted = true WHERE id = ?";

    @Override
    public Optional<Manufacturer> get(Long id) {
        Manufacturer manufacturer = null;
        try (Connection connection = ConnectionUtils.getConnection();
                PreparedStatement getAllManufacturerStatement = connection.prepareStatement(
                        GET_BY_ID_MANUFACTURERS_QUERY)) {
            getAllManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getAllManufacturerStatement.executeQuery();
            while (resultSet.next()) {
                manufacturer = Manufacturer
                    .builder()
                    .id(resultSet.getObject("id", Long.class))
                    .name(resultSet.getString("name"))
                    .country(resultSet.getString("country"))
                    .build();
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    String.format(GET_MANUFACTURER_BY_ID_EXCEPTION, id), e);
        }
        return Optional.ofNullable(manufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {

        List<Manufacturer> manufacturerList = new ArrayList<>();
        try (Connection connection = ConnectionUtils.getConnection();
                Statement getAllManufacturerStatement = connection.createStatement()) {
            ResultSet resultSet =
                    getAllManufacturerStatement.executeQuery(GET_ALL_MANUFACTURERS_QUERY);
            while (resultSet.next()) {
                Manufacturer manufacturer = Manufacturer
                        .builder()
                        .id(resultSet.getObject("id", Long.class))
                        .name(resultSet.getString("name"))
                        .country(resultSet.getString("country"))
                        .build();
                manufacturerList.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(GET_ALL_MANUFACTURERS_EXCEPTION, e);
        }
        return manufacturerList;
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtils.getConnection();
                PreparedStatement createManufacturerStatement = connection.prepareStatement(
                        CREATE_MANUFACTURERS_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    String.format(CREATE_MANUFACTURERS_EXCEPTION, manufacturer), e);
        }
        return manufacturer;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtils.getConnection();
                PreparedStatement updateManufacturerStatement = connection.prepareStatement(
                        UPDATE_MANUFACTURERS_QUERY)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException(
                    String.format(UPDATE_MANUFACTURERS_EXCEPTION, manufacturer), e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtils.getConnection();
                PreparedStatement deleteManufacturerByIdStatement = connection.prepareStatement(
                        DELETE_BY_ID_MANUFACTURERS_QUERY)) {
            deleteManufacturerByIdStatement.setLong(1, id);
            return deleteManufacturerByIdStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    String.format(DELETE_BY_ID_MANUFACTURERS_EXCEPTION, id), e);
        }
    }
}
