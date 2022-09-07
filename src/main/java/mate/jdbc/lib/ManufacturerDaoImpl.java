package mate.jdbc.lib;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

public class ManufacturerDaoImpl implements  ManufacturerDao{
    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer>  manufacturers = new ArrayList<>();
        Connection connection = ConnectionUtil.getConnection();
        try {
            Statement getALLManufacturersStatement = connection.createStatement();
            ResultSet resultSet = getALLManufacturersStatement.executeQuery("SELECT * FROM manufacturers");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                Long id = resultSet.getObject("id", Long.class);
                String country = resultSet.getString("country");
                Manufacturer manufacturer = new Manufacturer(id, name, country);
                manufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get all manufacturers from DB", e);
        }
        return manufacturers;
    }
}
