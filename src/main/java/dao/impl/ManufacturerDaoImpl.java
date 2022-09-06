package dao.impl;

import dao.ManufacturerDao;
import exception.DataProcessingException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Manufacturer;
import util.ConnectionUtil;

public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final String SELECT_ALL = "SELECT * FROM manufacturers";

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        //TODO при замене на PreparedStatement перестает выводить данные в консоль из базыданных
        try (Connection connection = ConnectionUtil.getConnection();
             Statement getAllManufacturerStatement = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturerStatement.executeQuery(SELECT_ALL);
            extract(allManufacturers, resultSet);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all formats from DB", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertQuery = "INSERT INTO manufacturers(name, country) values(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement createManufacturerStatement =
                     connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert format to DB", e);
        }
        return manufacturer;
    }

    private static void extract(List<Manufacturer> allManufacturers,
                                ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            Long id = resultSet.getObject("id", Long.class);
            String name = resultSet.getString("name");
            String country = resultSet.getString("country");
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setId(id);
            manufacturer.setName(name);
            manufacturer.setCountry(country);
            allManufacturers.add(manufacturer);
        }
    }
}
