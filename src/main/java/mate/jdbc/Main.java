package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer manufacturerVW = new Manufacturer("Volkswagen","Germany");
        Manufacturer manufacturerFiat = new Manufacturer("Fiat","Italy");
        Manufacturer manufacturerToyota = new Manufacturer("Toyota", "Japan");

        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        manufacturerDao.create(manufacturerVW);
        manufacturerDao.create(manufacturerFiat);
        manufacturerDao.create(manufacturerToyota);

        System.out.println("Currently in DB:");
        System.out.println(manufacturerDao.getAll());
        System.out.println();

        Optional<Manufacturer> manufacturerById1 = manufacturerDao.get(1L);
        System.out.println("Manufacturer by id = 1:" + manufacturerById1);
        Optional<Manufacturer> manufacturerById2 = manufacturerDao.get(2L);
        System.out.println("Manufacturer by id = 2:" + manufacturerById2);
        System.out.println();

        manufacturerFiat.setCountry("Unknown");
        System.out.println(manufacturerFiat.getId());
        manufacturerDao.update(manufacturerFiat);
        manufacturerDao.get(2L);
        System.out.println("Changed country of fiat manufacturer:" + manufacturerFiat);
        System.out.println();

        System.out.println("After updating manufacturer country");
        System.out.println("Currently in DB:");
        System.out.println(manufacturerDao.getAll());
        System.out.println("----------------------");

        manufacturerDao.delete(3L);
        System.out.println("After deleting manufacturer");
        System.out.println("Currently in DB:");
        System.out.println(manufacturerDao.getAll());
        System.out.println("----------------------");
    }
}

