package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector
            = Injector.getInstance(ManufacturerDao.class.getPackageName());
    private static final long testID = 1L;

    public static void main(String[] args) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("newName");
        manufacturer.setCountry("newCountry");
        manufacturer.setId(testID);
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        System.out.println(manufacturerDao.create(manufacturer));
        manufacturer.setName("updatedName");
        manufacturer.setCountry("updatedCountry");
        System.out.println(manufacturerDao.update(manufacturer));
        System.out.println(manufacturerDao.get(testID));
        System.out.println(manufacturerDao.delete(testID));
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
