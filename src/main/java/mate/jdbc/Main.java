package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        // create test
        Manufacturer getManufacturer =
                manufacturerDao.create(new Manufacturer("Kia", "South Korea"));
        System.out.println(getManufacturer);
        getManufacturer =
                manufacturerDao.create(new Manufacturer("Honda", "Japan"));
        getManufacturer =
                manufacturerDao.create(new Manufacturer("BMW", "Germany"));
        // get test
        System.out.println(manufacturerDao.get(2L));
        // delete test
        manufacturerDao.delete(3L);
        // update test
        manufacturerDao.update(new Manufacturer(2L,"Tesla", "USA"));
        // getAll test
        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        allManufacturers.forEach(System.out::println);
    }
}
