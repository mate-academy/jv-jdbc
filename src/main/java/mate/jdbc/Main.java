package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {

    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer;
        List<Manufacturer> allManufacturers;

        // Show all items in the table DB:
        allManufacturers = manufacturerDao.getAll();
        allManufacturers.forEach(System.out::println);

        // Add items in the table DB:
        /*manufacturer = new Manufacturer("Tesla", "USA");
        System.out.println(manufacturerDao.create(manufacturer).getId());
        manufacturer = new Manufacturer("Lincoln", "USA");
        manufacturerDao.create(manufacturer);
        manufacturer = new Manufacturer("Rolls Royce", "UK");
        manufacturerDao.create(manufacturer);*/

        // Show all items in the table DB:
        /*allManufacturers = manufacturerDao.getAll();
        allManufacturers.forEach(System.out::println);*/

        // Is deleted instance in DB:
        /*manufacturerDao.delete(15L);*/

        // Show all items in the table DB:
        /*allManufacturers = manufacturerDao.getAll();
        allManufacturers.forEach(System.out::println);*/

        // Show one instance:
        /*System.out.println(manufacturerDao.get(14L).get());*/

        // Update instance:
        /*manufacturer = new Manufacturer( 11L,"BMW", "Germany");
        System.out.println(manufacturerDao.update(manufacturer));*/
    }
}
