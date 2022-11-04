package mate.jdbc.dao.impl;

import mate.jdbc.dao.Dao;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionToDbUtil;
import mate.jdbc.util.DbPropertiesFileReader;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class ManufacturerDao implements Dao<Manufacturer> {
    private final Properties properties = DbPropertiesFileReader
            .getPropertiesFrom("src/main/resources/DBProperties");

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        return null;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String preparedRequest = "SELECT * FROM manufacturers WHERE id = ?";
        try (Connection connection = ConnectionToDbUtil.getConnection(properties);
             PreparedStatement statement = connection
                     .prepareStatement(preparedRequest, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, String.valueOf(id));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getManufacturer(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Connection connection = ConnectionToDbUtil.getConnection(properties);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM manufacturers;");
            while (resultSet.next()) {
                manufacturers.add(getManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error with connection to DB", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    private Manufacturer getManufacturer(ResultSet resultSet) {
        Long id = null;
        String name = null;
        String country = null;
        try {
            id = resultSet.getObject("id", Long.class);
            name = resultSet.getString("name");
            country = resultSet.getString("country");
        } catch (SQLException e) {
            throw new RuntimeException("Error reading columns from DB", e);
        }

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(id);
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        return manufacturer;
    }
}
