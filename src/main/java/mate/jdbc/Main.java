package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Manufacturer MANUFACTURER_MAZDA = new Manufacturer("Mazda", "Japan");
    private static final Manufacturer MANUFACTURER_GMC = new Manufacturer("GMC", "USA");
    
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        manufacturerDao.create(MANUFACTURER_MAZDA);
        manufacturerDao.create(MANUFACTURER_GMC);
        System.out.println(manufacturerDao.get(MANUFACTURER_MAZDA.getId()));
        System.out.println(manufacturerDao.get(MANUFACTURER_GMC.getId()));
        MANUFACTURER_MAZDA.setCountry("China");
        MANUFACTURER_MAZDA.setName("JAC");
        manufacturerDao.update(MANUFACTURER_MAZDA);
        System.out.println(manufacturerDao.get(MANUFACTURER_MAZDA.getId()));
        manufacturerDao.delete(MANUFACTURER_MAZDA.getId());
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
