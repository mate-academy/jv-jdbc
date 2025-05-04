package mate.jdbc;

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

        System.out.println(manufacturerDao.getAll());

        manufacturerToyota.setCountry("China");
        manufacturerDao.update(manufacturerToyota);
        System.out.println(manufacturerDao.getAll());

        manufacturerDao.delete(manufacturerToyota.getId());
        System.out.println(manufacturerDao.getAll());

        System.out.println(manufacturerDao
                .get(manufacturerVolkswagen.getId()));

        System.out.println(manufacturerDao.get(20L));
    }
}
