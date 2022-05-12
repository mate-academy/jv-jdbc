package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("FirstManufacturer");
        manufacturer.setCountry("Ukraine");
        manufacturerDao.create(manufacturer);
        manufacturer.setName("ModifiedFirstManufacturer");
        Manufacturer updatedManufacturer = manufacturerDao.update(manufacturer);
        manufacturerDao.delete(updatedManufacturer.getId());
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
