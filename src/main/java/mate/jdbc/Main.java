package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer manufacturer;
        System.out.println("----Adding a new manufacturer - Gett from United Kingdom: ");
        manufacturer = new Manufacturer();
        manufacturer.setName("Gett");
        manufacturer.setCountry("United Kingdom");
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        System.out.println(manufacturerDao.create(manufacturer));

        System.out.println("----Adding a new manufacturer - Uklon from Ukraine: ");
        manufacturer = new Manufacturer();
        manufacturer.setName("Uklon");
        manufacturer.setCountry("Ukraine");
        System.out.println(manufacturerDao.create(manufacturer));

        System.out.println("----Get all manufacturers :");
        List<Manufacturer> all = manufacturerDao.getAll();
        all.forEach(System.out::println);

        System.out.println("----Get manufacturer by index 2");
        Optional<Manufacturer> optionalManufacturer = manufacturerDao.get(2L);
        manufacturer = optionalManufacturer.get();
        System.out.println(manufacturer);

        System.out.println("----Make changes in manufacturer that we got from previous method "
                + "(set COUNTRY = Poland) ");
        manufacturer.setCountry("Poland");
        System.out.println(manufacturer);

        System.out.println("----Make Update in DB for this manufacturer");
        System.out.println(manufacturerDao.update(manufacturer));

        System.out.println("----Get all manufacturers :");
        all = manufacturerDao.getAll();
        all.forEach(System.out::println);

        System.out.println("----Delete from DB manufacturer by index 2");
        System.out.println(manufacturerDao.delete(2L));

        System.out.println("----Get all manufacturers :");
        all = manufacturerDao.getAll();
        all.forEach(System.out::println);
    }
}
