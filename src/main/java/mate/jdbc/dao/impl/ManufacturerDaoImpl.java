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
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String COUNTRY = "country";
    private static final String CREATE_STATEMENT =
            "INSERT INTO manufacturers (name, country) VALUES (?, ?);";
    private static final String GET_STATEMENT = "SELECT * FROM manufacturers WHERE id = ?;";
    private static final String GET_ALL_STATEMENT = "SELECT * FROM manufacturers;";
    private static final String UPDATE_STATEMENT =
            "UPDATE manufacturers SET name = ?, country = ? WHERE id = ?;";
    private static final String DELETE_STATEMENT =
            "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement createFormatStatement =
                     connection.prepareStatement(CREATE_STATEMENT,
                             Statement.RETURN_GENERATED_KEYS)) {
            createFormatStatement.setString(1, manufacturer.getName());
            createFormatStatement.setString(2, manufacturer.getCountry());
            createFormatStatement.executeUpdate();
            ResultSet generatedKeys = createFormatStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                manufacturer.setId(generatedKeys.getLong(1));
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t create manufacturer", e);
        }
    }

    private List<Manufacturer> getManufacturerFromResultSet(ResultSet resultSet) {
        List<Manufacturer> manufacturers = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(resultSet.getLong(ID));
                manufacturer.setName(resultSet.getString(NAME));
                manufacturer.setCountry(resultSet.getString(COUNTRY));
                manufacturers.add(manufacturer);
            }
        } catch (SQLException exceptionResultSet) {
            throw new DataProcessingException(
                    "Error processing the result set", exceptionResultSet);
        }
        return manufacturers;
    }


    @Override
    public Optional<Manufacturer> get(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement getStatement = connection.prepareStatement(GET_STATEMENT)) {
            getStatement.setLong(1, id);
            ResultSet resultSet = getStatement.executeQuery();
            List<Manufacturer> manufacturers = getManufacturerFromResultSet(resultSet);
            return manufacturers.isEmpty() ? Optional.empty() : Optional.of(manufacturers.get(0));
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer from DB", e);
        }
    }


    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             Statement getAllManufacturersStatement = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturersStatement.executeQuery(GET_ALL_STATEMENT);
            allManufacturers = getManufacturerFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
        return allManufacturers;
    }
    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement updateStatement = connection
                     .prepareStatement(UPDATE_STATEMENT)) {
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.setLong(3, manufacturer.getId());
            updateStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update Manufacturer", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement deleteStatement = connection
                     .prepareStatement(DELETE_STATEMENT)) {
            deleteStatement.setLong(1, id);
            return deleteStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete manufacturer by ID" + id, e);
        }
    }
}
