package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturerToyota =
                manufacturerDao.create(new Manufacturer("Toyota", "Japan"));
        Manufacturer manufacturerLandRover =
                manufacturerDao.create(new Manufacturer("Land Rover", "Great Britain"));
        Manufacturer manufacturerBmw =
                manufacturerDao.create(new Manufacturer("BMW", "Germany"));
        manufacturerDao.getAll().forEach(System.out::println);
        manufacturerDao.delete(manufacturerLandRover.getId());
        System.out.println(manufacturerDao.get(manufacturerToyota.getId()));
        manufacturerToyota.setCountry("USA");
        manufacturerDao.update(manufacturerToyota);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
