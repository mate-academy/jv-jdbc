package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer firstCar = new Manufacturer();
        firstCar.setName("Reno");
        firstCar.setCountry("France");
        Manufacturer secondCar = new Manufacturer();
        secondCar.setName("Toyota");
        secondCar.setCountry("Japan");
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        manufacturerDao.create(firstCar);
        manufacturerDao.create(secondCar);
        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        allManufacturers.forEach(System.out::println);
        System.out.println(manufacturerDao.get(firstCar.getId()));
        secondCar.setName("Mazda");
        manufacturerDao.update(secondCar);
        System.out.println(secondCar);
        manufacturerDao.delete(secondCar.getId());
    }
}
