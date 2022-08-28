package mate.jdbc;

import java.util.Arrays;
import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        List<Manufacturer> manufacturers = Arrays.asList(
                new Manufacturer("Toyota", "Japan"),
                new Manufacturer("Subaru", "Japan"),
                new Manufacturer("Volkswagen", "Germany"),
                new Manufacturer("Audi", "Germany"),
                new Manufacturer("Tesla", "USA"),
                new Manufacturer("Ford", "USA"),
                new Manufacturer("RENAULT", "FRANCE"),
                new Manufacturer("RENAULT", "FRANCE")
        );
        manufacturers.forEach(manufacturerDao::create);
        List<Manufacturer> manufacturersFromDb = manufacturerDao.getAll();
        manufacturersFromDb.forEach(System.out::println);
        Manufacturer manufacturerToUpdate = manufacturerDao.get(7L).get();
        manufacturerToUpdate.setName("FIAT");
        manufacturerToUpdate.setCountry("ITALY");
        manufacturerDao.update(manufacturerToUpdate);
        manufacturerDao.delete(8L);
        manufacturersFromDb = manufacturerDao.getAll();
        manufacturersFromDb.forEach(System.out::println);
    }
}

