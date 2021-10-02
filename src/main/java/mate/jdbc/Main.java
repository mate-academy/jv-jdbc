package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer porscheManufacturer = new Manufacturer("Porsche", "Germany");
        Manufacturer mustangManufacturer = new Manufacturer("Mustang", "USA");
        manufacturerDao.create(porscheManufacturer);
        manufacturerDao.create(mustangManufacturer);
        System.out.println(manufacturerDao.getAll());
        System.out.println(manufacturerDao.get(1l));
        Manufacturer car = new Manufacturer();
        car.setId(1l);
        car.setCountry("Ukraine");
        car.setName("Zaporozhets");
        manufacturerDao.update(car);
        System.out.println(manufacturerDao.getAll());
        manufacturerDao.delete(1l);
        System.out.println(manufacturerDao.getAll());


    }
}
