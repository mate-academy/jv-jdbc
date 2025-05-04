package mate.jdbc;

import mate.jdbc.dao.ManufactureDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufactureDao manufacturerDao = (ManufactureDao) injector
                    .getInstance(ManufactureDao.class);
        Manufacturer manufacturerAudi = new Manufacturer("Audi", "Germany");
        Manufacturer manufacturerToyota = new Manufacturer("Toyota", "Japan");
        manufacturerDao.create(manufacturerAudi);
        manufacturerDao.create(manufacturerToyota);
        System.out.println(manufacturerAudi);
        System.out.println(manufacturerToyota);
        manufacturerAudi.setCountry("Denmark");
        manufacturerDao.update(manufacturerAudi);
        manufacturerDao.get(manufacturerToyota.getId()).ifPresent(System.out::println);
        manufacturerDao.delete(manufacturerToyota.getId());
        System.out.println(manufacturerDao.getAll());
    }
}
