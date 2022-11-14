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
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final int ID = 1;
    private static final int NAME = 2;
    private static final int COUNTRY = 3;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createNewElement = connection
                           .prepareStatement(
                            "INSERT INTO manufacturers(name, country) value(?, ?);",
                                Statement.RETURN_GENERATED_KEYS)) {
            createNewElement.setString(1, manufacturer.getName());
            createNewElement.setString(2, manufacturer.getCountry());
            createNewElement.executeUpdate();
            ResultSet generatedKeys = createNewElement.getGeneratedKeys();
            if (generatedKeys.next()) {
                manufacturer.setId(generatedKeys.getObject(ID, Long.class));
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t add manufacturer" + manufacturer
                    + "to DB",e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getElement = connection
                        .prepareStatement(
                            "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false;")) {
            getElement.setLong(1,id);
            ResultSet resultSet = getElement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get element from db by id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturerList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllElements = connection
                        .prepareStatement(
                                "SELECT * FROM manufacturers WHERE is_deleted = false;")) {
            ResultSet resultSet = getAllElements.executeQuery();
            while (resultSet.next()) {
                manufacturerList.add(getManufacturer(resultSet));
            }
            return manufacturerList;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get data from db", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE manufacturers SET name = ?, country = ? "
                        + "WHERE id = ? AND is_deleted = false;")) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.setLong(3, manufacturer.getId());
            statement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update manufacturer " + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE manufacturers SET is_deleted = true WHERE id = ?;")) {
            statement.setLong(ID, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete element from db" + id,e);
        }
    }

    @Override
    public Manufacturer getManufacturer(ResultSet resultSet) {
        Manufacturer manufacturer = new Manufacturer();
        try {
            manufacturer.setId(resultSet.getObject(ID, Long.class));
            manufacturer.setName(resultSet.getString(NAME));
            manufacturer.setCountry(resultSet.getString(COUNTRY));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return manufacturer;
    }
}
