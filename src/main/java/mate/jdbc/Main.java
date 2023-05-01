package mate.jdbc;

import mate.jdbc.lib.Injector;
import mate.jdbc.lib.ManufacturerDao;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer zaz = new Manufacturer();
        zaz.setName("ZAZ");
        zaz.setCountry("Ukraine");
        manufacturerDao.create(zaz);
        Manufacturer jaguar = new Manufacturer();
        jaguar.setName("Jaguar");
        jaguar.setCountry("UK");
        manufacturerDao.create(jaguar);
        Manufacturer ford = new Manufacturer();
        ford.setName("Ford");
        ford.setCountry("Germany");
        manufacturerDao.create(ford);
        ford.setCountry("USA");
        manufacturerDao.update(ford);
        System.out.println(ford.getId());
        manufacturerDao.delete(zaz.getId());
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
