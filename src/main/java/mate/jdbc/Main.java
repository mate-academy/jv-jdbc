package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer("Mercedes", "Germany");
        Manufacturer createdManufacturer = manufacturerDao.create(manufacturer);
        System.out.println(manufacturer.equals(createdManufacturer));
        System.out.println(manufacturerDao.get(1L));
        System.out.println(manufacturerDao.getAll());
        manufacturer.setName("Bentley");
        manufacturer.setCountry("United Kingdom");
        Manufacturer updatedManufacturer = manufacturerDao.update(manufacturer);
        System.out.println(manufacturer.equals(updatedManufacturer));
        manufacturerDao.create(manufacturer);
        manufacturerDao.create(manufacturer);
        manufacturerDao.create(manufacturer);
        manufacturerDao.delete(3L);
        System.out.println(manufacturerDao.getAll());
    }
}
