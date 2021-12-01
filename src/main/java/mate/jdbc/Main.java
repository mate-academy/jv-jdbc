package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer firstCar = new Manufacturer("Toyota", "Japan");
        Manufacturer secondCar = new Manufacturer("KIA", "Korea");
        manufacturerDao.create(firstCar);
        manufacturerDao.create(secondCar);
        manufacturerDao.getAll().forEach(System.out::println);
        Manufacturer manufacturer = new Manufacturer();
        firstCar.setName("Ford");
        manufacturerDao.update(firstCar);
        System.out.println(manufacturerDao.get(firstCar.getId()));
        System.out.println(manufacturerDao.get(firstCar.getId()));
        manufacturerDao.delete(firstCar.getId());
    }
}
