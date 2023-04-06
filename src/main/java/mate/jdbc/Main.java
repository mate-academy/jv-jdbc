package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.manufacturer.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    public static final ManufacturerDao manufacturerDao
            = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

    public static void main(String[] args) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setCountry("Spain");
        manufacturer.setName("Yana");

        System.out.println(manufacturerDao.delete(3L));

        Optional<Manufacturer> manufacturerThree = manufacturerDao.get(1L);
        if (manufacturerThree.isPresent()) {
            System.out.println(manufacturerThree.get());
        } else {
            System.out.println("Error");
        }

        List<Manufacturer> manufacturerOne = manufacturerDao.getAll();
        System.out.println(manufacturerOne);

        Manufacturer update = manufacturerDao.update(manufacturer);
        System.out.println(update);

        Manufacturer manufacturerTwo = manufacturerDao.create(manufacturer);
        System.out.println(manufacturerTwo);
    }
}
