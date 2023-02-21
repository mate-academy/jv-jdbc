package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                         .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Mercedes");
        manufacturer.setCountry("Germany");
        manufacturerDao.create(manufacturer);
        System.out.println(manufacturerDao.getAll());
        System.out.println(manufacturerDao.get(4L));
        Manufacturer updateManufacturer = new Manufacturer();
        updateManufacturer.setId(6L);
        updateManufacturer.setName("Hyundai");
        updateManufacturer.setCountry("South Korea");
        manufacturerDao.update(updateManufacturer);
        manufacturerDao.delete(2L);
    }
}
