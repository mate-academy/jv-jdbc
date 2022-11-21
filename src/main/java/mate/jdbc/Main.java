package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Zaporojets");
        manufacturer.setCountry("Ukraine");
        manufacturerDao.create(manufacturer);
        manufacturerDao.get(1L);
        manufacturerDao.get(12L);
        manufacturerDao.getAll();
        Manufacturer updateManufacturer = new Manufacturer(1L, "Ford", "USA");
        manufacturerDao.update(updateManufacturer);
        manufacturerDao.delete(1L);
    }
}
