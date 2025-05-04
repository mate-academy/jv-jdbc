package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector =
            Injector.getInstance("mate.jdbc");
    private static ManufacturerDao manufacturerDao = null;

    public static void main(String[] args) {
        manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer jeepManufacturer = new Manufacturer();
        jeepManufacturer.setName("Toyota");
        jeepManufacturer.setCountry("Japan");
        manufacturerDao.create(jeepManufacturer);
        List<Manufacturer> getAllManufacturer = manufacturerDao.getAll();
        System.out.println(getAllManufacturer);
        Optional<Manufacturer> getManufacturerById = manufacturerDao.get(1L);
        System.out.println(getManufacturerById);
    }
}
