package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer car1 = new Manufacturer("Car1", "SomeCountry");
        manufacturerDao.create(car1);
        Manufacturer car2 = new Manufacturer("Car2", "SomeCountry");
        manufacturerDao.create(car2);
        Manufacturer car3 = new Manufacturer("Car3", "SomeCountry");
        manufacturerDao.create(car3);

        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        for (Manufacturer manufacturer : manufacturers) {
            System.out.println(manufacturer);
        }

        System.out.println(manufacturerDao.get(car1.getId()));

        manufacturerDao.update(car1);

        manufacturerDao.delete(car1.getId());
    }
}
