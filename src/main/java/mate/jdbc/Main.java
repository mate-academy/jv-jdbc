package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer ford = new Manufacturer("Ford", "USA");
        Manufacturer honda = new Manufacturer("Honda", "Japan");
        Manufacturer bmw = new Manufacturer("BMW", "Germany");
        manufacturerDao.create(ford);
        manufacturerDao.create(honda);
        manufacturerDao.create(bmw);
        System.out.println(manufacturerDao.getAll());
        manufacturerDao.update(ford);
        System.out.println(manufacturerDao.get(2L));
        System.out.println(manufacturerDao.delete(bmw.getId()));
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
