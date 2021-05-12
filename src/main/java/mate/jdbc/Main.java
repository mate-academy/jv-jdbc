package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Manufacturer MANUFACTURER_MAZDA = new Manufacturer(20L, "Mazda", "Japan");
    private static final Manufacturer MANUFACTURER_GMC = new Manufacturer(20L, "GMC", "USA");
    private static final Long MANUFACTURER_ID = 20L;
    
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(
                ManufacturerDao.class);
        manufacturerDao.getAll().forEach(System.out::println);
        Manufacturer manufacturer = MANUFACTURER_MAZDA;
        manufacturerDao.create(manufacturer);
        System.out.println(manufacturerDao.get(MANUFACTURER_ID));
        manufacturer = MANUFACTURER_GMC;
        manufacturerDao.update(manufacturer);
        System.out.println(manufacturerDao.get(MANUFACTURER_ID));
        manufacturerDao.delete(MANUFACTURER_ID);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
