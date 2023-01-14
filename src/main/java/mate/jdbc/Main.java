package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static final long NON_EXISTING_DRIVER_ID = 11L;
    private static final long MANUFACTURER_ID = 3L;

    public static void main(String[] args) {

        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        System.out.println(manufacturerDao.getAll());
        Manufacturer honda = new Manufacturer();
        honda.setName("Honda");
        honda.setCountry("Germany");
        System.out.println(manufacturerDao.create(honda));
        System.out.println(manufacturerDao.getAll());
        honda.setCountry("Japan");
        System.out.println(manufacturerDao.update(honda));
        System.out.println(manufacturerDao.getAll());
        System.out.println(manufacturerDao.delete(NON_EXISTING_DRIVER_ID));
        System.out.println(manufacturerDao.getAll());
        manufacturerDao.get(MANUFACTURER_ID);
    }
}
