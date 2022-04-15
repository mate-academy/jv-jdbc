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
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(manufacturerDao.get(mustangManufacturer.getId()));
        Manufacturer car = new Manufacturer();
        car.setId(mustangManufacturer.getId());
        car.setCountry("Ukraine");
        car.setName("Zaporozhets");
        manufacturerDao.update(car);
        manufacturerDao.getAll().forEach(System.out::println);
        manufacturerDao.delete(mustangManufacturer.getId());
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
