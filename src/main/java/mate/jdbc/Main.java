package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        final ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);

        Manufacturer manufacturerVolkswagen = new Manufacturer();
        Manufacturer manufacturerToyota = new Manufacturer();
        manufacturerVolkswagen.setName("Volkswagen");
        manufacturerToyota.setName("Toyota");
        manufacturerVolkswagen.setCountry("Germany");
        manufacturerToyota.setCountry("Japan");

        manufacturerDao.create(manufacturerVolkswagen);
        manufacturerDao.create(manufacturerToyota);

        List<Manufacturer> all = manufacturerDao.getAll();
        System.out.println(all.toString());

        manufacturerToyota.setCountry("China");
        manufacturerDao.update(manufacturerToyota);
        List<Manufacturer> all2 = manufacturerDao.getAll();
        System.out.println(all2);

        manufacturerDao.delete(manufacturerToyota.getId());
        List<Manufacturer> all3 = manufacturerDao.getAll();
        System.out.println(all3);

        Optional<Manufacturer> manufacturerVolk = manufacturerDao
                .get(manufacturerVolkswagen.getId());
        System.out.println(manufacturerVolk.toString());

        Optional<Manufacturer> optionalManufacturer = manufacturerDao.get(20L);
        System.out.println(optionalManufacturer);
    }
}
