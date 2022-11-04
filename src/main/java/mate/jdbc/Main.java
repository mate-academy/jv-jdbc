package mate.jdbc;

import mate.jdbc.dao.Dao;
import mate.jdbc.dao.impl.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionToDbUtil;
import mate.jdbc.util.DbPropertiesFileReader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

public class Main {
    private static Injector injector = Injector.getInstance("main.jdbc");

    public static void main(String[] args) {
//        Properties dbproperties = DbPropertiesFileReader
//                .getPropertiesFrom("src/main/resources/DBProperties");
//        System.out.println(dbproperties);
//        Connection connection = ConnectionToDbUtil.getConnection(dbproperties);
//
//        try {
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM manufacturers");
//            resultSet.next();
//            System.out.println(resultSet.getString("name"));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        Dao<?> dao = (Dao<?>) injector.getInstance(Manufacturer.class);
        Dao<Manufacturer> dao = new ManufacturerDao();
        List<Manufacturer> list = dao.getAll();
        System.out.println(list.get(0).getName());

        System.out.println(dao.get(1L));
    }
}
