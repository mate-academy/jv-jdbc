package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        System.out.println("Table: ");
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("-------------------------------");
        Manufacturer bridgestone = new Manufacturer("Bridgestone", "Japan");
        System.out.println("Created record: " + manufacturerDao.create(bridgestone));
        Manufacturer cooper = new Manufacturer(4L, "Cooper", "USA");
        System.out.println("Updated record: " + manufacturerDao.update(cooper));
        System.out.println("Get by id " + cooper.getId()
                + " record: " + manufacturerDao.get(cooper.getId()));
        System.out.println("Record " + cooper.getName()
                + " is deleted: " + manufacturerDao.delete(cooper.getId()));
        System.out.println("-------------------------------");
        System.out.println("Table: ");
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
