package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer apple = new Manufacturer("Apple", "USA");
        Manufacturer samsung = new Manufacturer("Samsung", "South Korea");
        Manufacturer xiaomi = new Manufacturer("Xiaomi", "China");

        manufacturerDao.create(apple);
        manufacturerDao.create(samsung);
        manufacturerDao.create(xiaomi);

        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        allManufacturers.forEach(System.out::println);

        manufacturerDao.delete(apple.getId());
        manufacturerDao.update(new Manufacturer(samsung.getId(), "LG", "South Korea"));

        allManufacturers = manufacturerDao.getAll();
        allManufacturers.forEach(System.out::println);
    }
}
