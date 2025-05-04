package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {

        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        System.out.println("create method test");
        manufacturerDao.create(new Manufacturer("BMW", "Germany"));
        manufacturerDao.create(new Manufacturer("Mercedes", "Germany"));
        manufacturerDao.create(new Manufacturer("Volkswagen", "Germany"));
        manufacturerDao.create(new Manufacturer("Honda", "Japan"));
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("get method test");
        manufacturerDao.get(3L).ifPresent(System.out::println);
        System.out.println("delete method test:");
        manufacturerDao.delete(7L);
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("update method test:");
        Manufacturer manufacturer = new Manufacturer(9L,"Tesla", "USA");
        manufacturerDao.update(manufacturer);
        manufacturerDao.getAll().forEach(System.out::println);

    }
}
