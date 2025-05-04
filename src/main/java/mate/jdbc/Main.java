package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        List<Manufacturer> listOfManufacturers =
                List.of(new Manufacturer("Toyota", "Japan"),
                        new Manufacturer("Ford", "USA"),
                        new Manufacturer("Accent", "South Korea"));
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        for (Manufacturer manufacturer: listOfManufacturers) {
            manufacturerDao.create(manufacturer);
        }
        System.out.println(manufacturerDao.get(1L));
        System.out.println(manufacturerDao.getAll());
        System.out.println(manufacturerDao.update(new Manufacturer(2L, "Mustang", "USA")));
        System.out.println(manufacturerDao.delete(1L));
    }
}
