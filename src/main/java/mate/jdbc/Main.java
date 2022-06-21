package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.impl.ManufacturerDaoImpl;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class Main {
    private static final Injector injector = Injector.getInstance("mate");
    public static void main(String[] args) {
        ManufacturerDao manufacturerDao= new ManufacturerDaoImpl();
        List<Manufacturer> all = manufacturerDao.getAll();
        all.forEach(System.out::println);

    }
}
