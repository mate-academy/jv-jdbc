package mate.jdbc.dao;

import mate.jdbc.lib.Dao;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao{
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        return null;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        Connection connection = ConnectionUtil.getConnection();
        Statement getAllManufacturersStatement = null;
        try {
            getAllManufacturersStatement = connection.createStatement();
            ResultSet resultSet = getAllManufacturersStatement.executeQuery("SELECT * FROM manufacturers");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Long id = resultSet.getLong("id");
                allManufacturers.add(new Manufacturer(name, country, id));

            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get all manufacturers from DB", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        System.out.println("Hello");
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
