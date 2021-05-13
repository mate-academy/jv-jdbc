package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);

        Manufacturer samsung = new Manufacturer("Samsung", "South Korea");
        Manufacturer xiaomi = new Manufacturer("Xiaomi", "China");
        Manufacturer apple = new Manufacturer("Apple", "USA");
        Manufacturer nokia = new Manufacturer("Nokia", "Finland");

        manufacturerDao.create(samsung);
        manufacturerDao.create(xiaomi);
        manufacturerDao.create(apple);
        manufacturerDao.create(nokia);
        System.out.println(manufacturerDao.getAll() + System.lineSeparator());

        manufacturerDao.delete(xiaomi.getId());
        System.out.println(manufacturerDao.getAll() + System.lineSeparator());

        System.out.println(manufacturerDao.get(1L) + System.lineSeparator()
                + manufacturerDao.get(88L) + System.lineSeparator());

        nokia.setName("newNokia");
        manufacturerDao.update(nokia);
        System.out.println(manufacturerDao.getAll());
    }
}
