package mate.jdbc;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");


    public static void main(String[] args) {
        Manufacturer manufacturer = null;
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        manufacturer = new Manufacturer(1L,"Kia", "Korea");
        manufacturerDao.create(manufacturer);
        manufacturer = new Manufacturer(2L,"Porsche", "Germany");
        manufacturerDao.create(manufacturer);
        manufacturer = new Manufacturer(3L,"Tesla", "USA");
        manufacturerDao.create(manufacturer);
        manufacturer = new Manufacturer(4L,"Toyota", "Japan");
        manufacturerDao.create(manufacturer);


        manufacturerDao.getAll().forEach(m -> System.out.println("manufacturer" + m.toString()));

        manufacturerDao.delete(2L);
        manufacturerDao.getAll().forEach(m -> System.out.println("manufacturer" + m.toString()));
        manufacturerDao.get(1L);
        manufacturerDao.getAll().forEach(m -> System.out.println("manufacturer" + m.toString()));
        manufacturerDao.update(new Manufacturer(3L, "Audi", "Germany"));
        manufacturerDao.getAll().forEach(m -> System.out.println("manufacturer" + m.toString()));
    }
}
