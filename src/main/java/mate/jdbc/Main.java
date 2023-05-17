package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        printAll(manufacturerDao);
        manufacturerDao.delete(10L);
        printAll(manufacturerDao);
        Manufacturer volkswagen = new Manufacturer("Volkswagen", "Germany");
        manufacturerDao.create(volkswagen);
        printAll(manufacturerDao);
        Manufacturer manufacturerFromDB = manufacturerDao.get(11L).get();
        System.out.println("Manufacturer from DB by id: " + manufacturerFromDB);
        Manufacturer toyota = new Manufacturer(13L, "Toyota", "Japan");
        manufacturerDao.update(toyota);
        printAll(manufacturerDao);

    }

    private static void printAll(ManufacturerDao manufacturerDao) {
        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        allManufacturers.forEach(System.out::println);
    }
}
