package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer ford = new Manufacturer();
        ford.setName("Renault");
        ford.setCountry("France");
        Manufacturer zaz = new Manufacturer();
        zaz.setName("Daewoo");
        zaz.setCountry("Ukraine");

        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        manufacturerDao.create(ford);
        manufacturerDao.create(zaz);

        System.out.println(manufacturerDao.get(zaz.getId()));
        manufacturerDao.getAll().forEach(System.out::println);

        zaz.setName("ZAZ");
        manufacturerDao.update(zaz);
        System.out.println(manufacturerDao.get(zaz.getId()));
        manufacturerDao.delete(ford.getId());
    }
}
