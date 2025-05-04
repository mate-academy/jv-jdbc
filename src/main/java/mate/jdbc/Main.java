package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);

        System.out.println("Creating new rows: ");
        Manufacturer manufacturerApple = new Manufacturer("Apple", "USA");
        manufacturerDao.create(manufacturerApple);
        Manufacturer manufacturerSamsung = new Manufacturer("Samsung", "South Korea");
        manufacturerDao.create(manufacturerSamsung);
        Manufacturer manufacturerXiaomi = new Manufacturer("Xiaomi", "China");
        manufacturerDao.create(manufacturerXiaomi);
        manufacturerDao.getAll().forEach(System.out::println);

        System.out.println("Getting row by ID: ");
        System.out.println(manufacturerDao.get(manufacturerApple.getId()).get());

        System.out.println("Updating existing row: ");
        manufacturerApple.setCountry("United States");
        manufacturerDao.update(manufacturerApple);
        System.out.println(manufacturerDao.get(manufacturerApple.getId()).get());

        System.out.println("Deleting row by ID: ");
        manufacturerDao.delete(manufacturerXiaomi.getId());
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
