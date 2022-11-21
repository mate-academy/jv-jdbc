package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        final ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer toyota = new Manufacturer();
        toyota.setName("toyota");
        toyota.setCountry("Japan");
        Manufacturer bmw = new Manufacturer();
        bmw.setName("BMW");
        bmw.setCountry("Germany");
        manufacturerDao.create(toyota);
        manufacturerDao.create(bmw);
        toyota.setName("toyota-s");
        manufacturerDao.update(toyota);
        Optional<Manufacturer> optionalManufacturer = manufacturerDao.get(bmw.getId());
        optionalManufacturer.ifPresent(System.out::println);
        manufacturerDao.delete(bmw.getId());
        System.out.println(manufacturerDao.getAll());
    }
}
