package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer germanManufacturer = new Manufacturer();
        germanManufacturer.setName("Mercedes");
        germanManufacturer.setCountry("Germany");
        germanManufacturer = manufacturerDao.create(germanManufacturer);
        System.out.println(manufacturerDao.get(germanManufacturer.getId()));
        manufacturerDao.create(germanManufacturer);
        germanManufacturer.setName("Volkswagen");
        germanManufacturer = manufacturerDao.update(germanManufacturer);
        System.out.println(manufacturerDao.get(germanManufacturer.getId()));
        System.out.println(manufacturerDao.delete(germanManufacturer.getId()));
        manufacturerDao.create(germanManufacturer);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
