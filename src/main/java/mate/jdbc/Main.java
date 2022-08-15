package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        //Manufacturer manufacturer = new Manufacturer("VW", "Germany");
        //manufacturerDao.create(manufacturer);
        Manufacturer manufacturer = new Manufacturer("Car name2", "Car country2");
        manufacturer.setId(1L);
        System.out.println(manufacturerDao.update(manufacturer));
        //manufacturerDao.getAll().forEach(System.out::println);
    }
}
