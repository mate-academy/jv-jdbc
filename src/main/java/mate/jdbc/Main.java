package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector =
            Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer carManufacturer = new Manufacturer();
        carManufacturer.setName("Tesla");
        carManufacturer.setCountry("USA");
        manufacturerDao.create(carManufacturer);

        List<Manufacturer> getAllManufacturer = manufacturerDao.getAll();
        System.out.println(getAllManufacturer);

        Optional<Manufacturer> getManufacturerById = manufacturerDao.get(1L);
        System.out.println(getManufacturerById);

        carManufacturer.setName("Volvo");
        carManufacturer.setCountry("Sweden");
        System.out.println(manufacturerDao.update(carManufacturer));

        System.out.println(manufacturerDao.delete(3L));
    }
}
