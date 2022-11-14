package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer audiManufacturer = new Manufacturer("Audi", "Germany");
        Manufacturer ferrariManufacturer = new Manufacturer("ferrari", "Italy");
        manufacturerDao.create(audiManufacturer);
        manufacturerDao.create(ferrariManufacturer);
        Optional<Manufacturer> optionalManufacturer = manufacturerDao.get(audiManufacturer.getId());
        optionalManufacturer.ifPresent(System.out::println);
        ferrariManufacturer.setName("Ferrari");
        manufacturerDao.update(ferrariManufacturer);
        System.out.println(manufacturerDao.getAll());
        manufacturerDao.delete(audiManufacturer.getId());
        System.out.println(manufacturerDao.getAll());
    }
}
