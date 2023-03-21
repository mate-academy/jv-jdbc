package mate.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

public class Main {
    public static void main(String[] args) {
        Connection connection = ConnectionUtil.getConnection();
        Statement getAllFormatsStatement = null;
        try {
            getAllFormatsStatement = connection.createStatement();
            ResultSet resultSet = getAllFormatsStatement
                    .executeQuery("SELECT * FROM manufacturers");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Long id = resultSet.getObject("id", Long.class);
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(id);
                manufacturer.setName(name);
                manufacturer.setCountry(country);
                System.out.println(manufacturer);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get all formats from DB", e);
        }

    }
}
