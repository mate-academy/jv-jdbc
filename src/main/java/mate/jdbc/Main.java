package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {

        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        System.out.println(
                manufacturerDao.create(new Manufacturer("Daewoo", "South Korea")));
        System.out.println(
                manufacturerDao.create(new Manufacturer("Chevrolet", "USA")));
        System.out.println(
                manufacturerDao.create(new Manufacturer("Opel", "Germany")));

        System.out.println(manufacturerDao.get(1L));

        manufacturerDao.update(new Manufacturer(2L, "Chevrolet", "Ukraine"));
        System.out.println(manufacturerDao.get(2L));

        for (Manufacturer manufacturer : manufacturerDao.getAll()) {
            System.out.println(manufacturer);
        }

        manufacturerDao.delete(1L);
        for (Manufacturer manufacturer : manufacturerDao.getAll()) {
            System.out.println(manufacturer);
        }
    }
}
