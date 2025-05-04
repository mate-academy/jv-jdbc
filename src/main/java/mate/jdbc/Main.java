package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {

        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        System.out.println("\n" + "create(Manufacturer manufacturer) method test:");
        manufacturerDao.create(new Manufacturer("Ford", "USA"));
        manufacturerDao.create(new Manufacturer("Ford", "Spain"));
        manufacturerDao.create(new Manufacturer("Ford", "Germany"));
        manufacturerDao.create(new Manufacturer("Tesla", "USA"));
        manufacturerDao.create(new Manufacturer("BMW", "Germany"));
        manufacturerDao.create(new Manufacturer("Mercedes", "Germany"));
        manufacturerDao.create(new Manufacturer("Volkswagen", "Germany"));
        manufacturerDao.create(new Manufacturer("Volkswagen", "China"));
        manufacturerDao.create(new Manufacturer("Honda", "Japan"));
        manufacturerDao.getAll().forEach(System.out::println);

        System.out.println("\n" + "getAll() method test:");
        manufacturerDao.getAll().forEach(System.out::println);

        System.out.println("\n" + "get(Long id) method test");
        manufacturerDao.get(3L).ifPresent(System.out::println);

        System.out.println("\n" + "delete(Long id) method test:");
        manufacturerDao.delete(7L);
        manufacturerDao.getAll().forEach(System.out::println);

        System.out.println("\n" + "update(Manufacturer manufacturer) method test:");
        Manufacturer manufacturer = new Manufacturer(9L,"Honda", "China");
        manufacturerDao.update(manufacturer);
        manufacturerDao.getAll().forEach(System.out::println);

    }
}
