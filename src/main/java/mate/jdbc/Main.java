package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                    .getInstance(ManufacturerDao.class);
        Manufacturer manufacturerAudi = new Manufacturer("Audi", "Germany");
        Manufacturer manufacturerToyota = new Manufacturer("Toyota", "Japan");
        manufacturerAudi = manufacturerDao.create(manufacturerAudi);
        manufacturerToyota = manufacturerDao.create(manufacturerToyota);
        System.out.println(manufacturerAudi);
        System.out.println(manufacturerToyota);
        manufacturerAudi.setCountry("Denmark");
        manufacturerDao.update(manufacturerAudi);
        manufacturerDao.get(manufacturerToyota.getId()).ifPresent(System.out::println);
        manufacturerDao.delete(manufacturerToyota.getId());
        System.out.println(manufacturerDao.getAll());
    }
}
