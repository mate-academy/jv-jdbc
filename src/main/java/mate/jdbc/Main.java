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
        Manufacturer frenchManufacturer = new Manufacturer();
        frenchManufacturer.setCountry("France");
        frenchManufacturer.setName("Renault");

        Manufacturer germanManufacturer = new Manufacturer();
        germanManufacturer.setCountry("Germany");
        germanManufacturer.setName("Volkswagen");

        Manufacturer italianManufacturer = new Manufacturer();
        italianManufacturer.setCountry("Italy");
        italianManufacturer.setName("Fiat");

        List<Manufacturer> manufacturers = List
                .of(frenchManufacturer, germanManufacturer, italianManufacturer);
        for (Manufacturer manufacturer : manufacturers) {
            System.out.println(manufacturerDao.create(manufacturer));
        }

        manufacturerDao.get(2L).ifPresent(System.out::println);
        manufacturerDao.get(10L).ifPresent(System.out::println);

        manufacturerDao.getAll().forEach(System.out::println);

        germanManufacturer.setName("Audi");
        manufacturerDao.update(germanManufacturer);
        manufacturerDao.delete(3L);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
