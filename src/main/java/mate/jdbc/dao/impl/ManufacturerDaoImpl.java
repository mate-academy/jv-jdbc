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
import mate.jdbc.lib.Dao;
import mate.jdbc.lib.DataProcessingException;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allFormat = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                 Statement getAllFormatsStatement = connection.createStatement()) {
            ResultSet resultSet = getAllFormatsStatement
                    .executeQuery("SELECT * FROM manufacturers WHERE is_deleted = FALSE");
            while (resultSet.next()) {
                Manufacturer manufacturer = createManufacturer(resultSet);
                allFormat.add(manufacturer);
            }
        } catch (SQLException throwable) {
            throw new DataProcessingException("Can't get all formats from db", throwable);
        }
        return allFormat;
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String createQuery = "INSERT INTO manufacturers(name, country) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement
                         = connection.prepareStatement(createQuery,
                         Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException throwable) {
            throw new DataProcessingException("Can't add new manufacturer "
                    + manufacturer + " to data bases", throwable);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getQuery = "SELECT * FROM manufacturers "
                + "WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getStatement = connection.prepareStatement(getQuery)) {
            getStatement.setLong(1, id);
            ResultSet resultSet = getStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(createManufacturer(resultSet));
            }
        } catch (SQLException throwable) {
            throw new DataProcessingException("Can't get manufacturer by id " + id, throwable);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleted(Long id) {
        String softDeleteQuery = "UPDATE manufacturers "
                + "SET is_deleted = TRUE WHERE id = (?)";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement softDeleteStatement
                         = connection.prepareStatement(softDeleteQuery)) {
            softDeleteStatement.setLong(1, id);
            return softDeleteStatement.executeUpdate() == 1;
        } catch (SQLException throwable) {
            throw new DataProcessingException("Can't delete manufacturer by id " + id, throwable);
        }
    }

    @Override
    public Manufacturer updated(Manufacturer manufacturer) {
        String updateQuery = "UPDATE manufacturers "
                + "SET name = ?, country = ? WHERE id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2,manufacturer.getCountry());
            preparedStatement.setLong(3,manufacturer.getId());
            preparedStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException throwable) {
            throw new DataProcessingException("Can't update DB from manufacturer "
                    + manufacturer, throwable);
        }
    }

    private Manufacturer createManufacturer(ResultSet resultSet) {
        try {
            return new Manufacturer(
                    resultSet.getObject("id", Long.class),
                    resultSet.getString("name"),
                    resultSet.getString("country"));
        } catch (SQLException throwable) {
            throw new DataProcessingException("Can't create manufacturer from "
                    + resultSet, throwable);
        }
    }
}
