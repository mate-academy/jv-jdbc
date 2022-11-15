package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer koenigsegg = new Manufacturer("Koenigsegg", "Sweden");
        Manufacturer bugatti = new Manufacturer("Bugatti", "France");
        manufacturerDao.create(koenigsegg);
        manufacturerDao.create(bugatti);
        Optional<Manufacturer> optionalManufacturer = manufacturerDao.get(koenigsegg.getId());
        optionalManufacturer.ifPresent(System.out::println);
        bugatti.setCountry("Germany");
        bugatti.setName("BugattiNEW");
        manufacturerDao.update(bugatti);
        System.out.println(manufacturerDao.getAll());
        manufacturerDao.delete(koenigsegg.getId());
        System.out.println(manufacturerDao.getAll());
    }
}
