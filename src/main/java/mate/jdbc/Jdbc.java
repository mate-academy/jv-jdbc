package mate.jdbc;

import mate.jdbc.db.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Jdbc {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer manufacturer = new Manufacturer("Toyota", "Japan");
        Manufacturer manufacturer2 = new Manufacturer("BMW", "Germany");
        Manufacturer manufacturer3 = new Manufacturer("Ford", "USA");

        manufacturerDao.create(manufacturer2);
        manufacturerDao.create(manufacturer3);

        Manufacturer man = manufacturerDao.create(manufacturer);
        man.setName("Tata");
        man.setCountry("India");
        manufacturerDao.update(man);
        manufacturerDao.delete(man.getId());

        manufacturerDao.getAll().forEach(System.out::println);
    }
}
