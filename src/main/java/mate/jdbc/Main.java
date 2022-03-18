package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");
    private static final ManufacturerDao manufacturerDao =
            (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

    public static void main(String[] args) {
        System.out.println("Created: "
                + manufacturerDao.create(new Manufacturer("Jeep", "USA")));
        System.out.println("Created: "
                + manufacturerDao.create(new Manufacturer("Nissan", "Japan")));
        System.out.println("Element of the DB with id = 1: " + manufacturerDao.get(1L));
        System.out.println("Element of the DB with id = 13 updated by: "
                + manufacturerDao.update(new Manufacturer(13L, "Kia", "Korea")));
        System.out.println("Element of the DB with id = 14 was deleted"
                + manufacturerDao.delete(14L));
        System.out.println("All element of the DB: ");
        manufacturerDao.getAll().forEach(System.out::println);
    }

}
