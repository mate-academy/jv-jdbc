package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.jdbc");
    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer porscheManufacturer = new Manufacturer("Porsche", "Germany");
        Manufacturer mustangManufacturer = new Manufacturer("Mustang", "USA");
        manufacturerDao.create(porscheManufacturer);
        manufacturerDao.create(mustangManufacturer);
        System.out.println(manufacturerDao.getAll());
        System.out.println(manufacturerDao.get(1L));
        Manufacturer car = new Manufacturer();
        car.setId(1L);
        car.setCountry("Ukraine");
        car.setName("Zaporozhets");
        manufacturerDao.update(car);
        System.out.println(manufacturerDao.getAll());
        manufacturerDao.delete(1L);
        System.out.println(manufacturerDao.getAll());
    }
}
