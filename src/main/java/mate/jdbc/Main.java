package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("apple");
        manufacturer.setCountry("usa");
        Manufacturer createdManufacturer = manufacturerDao.create(manufacturer);
        manufacturerDao.get(createdManufacturer.getId());
        manufacturerDao.getAll();
        Manufacturer newManufacturer = new Manufacturer();
        newManufacturer.setName("samsung");
        newManufacturer.setCountry("south korea");
        newManufacturer.setId(1L);
        Manufacturer updatedManufacturer = manufacturerDao.update(newManufacturer);
        manufacturerDao.delete(updatedManufacturer.getId());
    }
}
