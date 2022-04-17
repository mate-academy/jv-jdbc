package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer testManufacturer = new Manufacturer(null, "Nissan", "Japan");
        manufacturerDao.create(testManufacturer);
        testManufacturer.setName("BMW");
        testManufacturer.setCountry("Germany");
        manufacturerDao.update(testManufacturer);
        manufacturerDao.delete(testManufacturer.getId());
    }
}
