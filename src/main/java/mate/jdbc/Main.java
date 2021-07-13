package mate.jdbc;

import mate.jdbc.lib.Injector;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer bridgestone = new Manufacturer("Bridgestone", "Japan");
        Manufacturer cooper = new Manufacturer(4L, "Cooper", "USA");

        System.out.println(manufacturerDao.getAll());
        System.out.println(manufacturerDao.create(bridgestone));
        System.out.println(manufacturerDao.update(cooper));
        System.out.println(manufacturerDao.get(cooper.getId()));
        System.out.println(manufacturerDao.delete(cooper.getId()));
        System.out.println(manufacturerDao.getAll());
    }
}
