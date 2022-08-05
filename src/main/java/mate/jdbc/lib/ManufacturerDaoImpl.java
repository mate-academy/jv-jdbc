package mate.jdbc.lib;

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
        List<Manufacturer> manufacturers = new ArrayList();
        Connection connection = ConnectionUtil.getConnection();
        Statement getAllManufacturerStatement = null;
        try {
            getAllManufacturerStatement = connection.createStatement();
            ResultSet resultSet = getAllManufacturerStatement.executeQuery("SELECT * FROM manufacturer");
            while(resultSet.next()) {
                Manufacturer manufacturer = new Manufacturer(resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("country"));
                manufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get all manufacturer from DB", e);
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
}
