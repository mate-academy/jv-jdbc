package mate.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate");

        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
//        List<Manufacturer> allManufacturer = manufacturerDao.getAll();
//        for (Manufacturer manufacturer : allManufacturer) {
//            System.out.println(manufacturer);
//        }

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Bogdan");
        manufacturer.setCountry("Ukraine");
        manufacturerDao.create(manufacturer);
        System.out.println(manufacturer);
    }
}
