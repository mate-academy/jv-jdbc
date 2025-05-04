package mate.jdbc;

import java.util.Arrays;
import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        List<Manufacturer> manufacturersToDb = Arrays.asList(
                new Manufacturer("Renault", "France"),
                new Manufacturer("Volkswagen", "Germany"),
                new Manufacturer("Audi", "Germany"),
                new Manufacturer("bmw", "GERMANY"),
                new Manufacturer("Opel", "Germany"),
                new Manufacturer("Opel", "Germany")
        );
        manufacturersToDb.forEach(manufacturerDao::create);
        List<Manufacturer> manufacturersFromDb = manufacturerDao.getAll();
        manufacturersFromDb.forEach(System.out::println);
        Manufacturer manufacturerToUpdate = manufacturerDao.get(4L).get();
        manufacturerToUpdate.setName("BMW");
        manufacturerToUpdate.setCountry("Germany");
        manufacturerDao.delete(6L);
        manufacturerDao.update(manufacturerToUpdate);
        manufacturersFromDb = manufacturerDao.getAll();
        manufacturersFromDb.forEach(System.out::println);
    }
}
