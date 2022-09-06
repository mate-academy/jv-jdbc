package dao.impl;

import dao.ManufacturerDao;
import exception.DataProcessingException;
import model.Manufacturer;
import util.ConnectionUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final String SELECT_ALL = "SELECT * FROM manufacturers";

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        Connection connection = ConnectionUtil.getConnection();
        Statement getAllManufacturerStatement = null;
        try {
            getAllManufacturerStatement = connection.createStatement();
            ResultSet resultSet = getAllManufacturerStatement.executeQuery(SELECT_ALL);
            extract(allManufacturers, resultSet);
        } catch (
                SQLException e) {
            throw new DataProcessingException("Can't get all formats from DB", e);
        }
        return allManufacturers;
    }

    private static void extract(List<Manufacturer> allManufacturers, ResultSet resultSet) throws SQLException {
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
