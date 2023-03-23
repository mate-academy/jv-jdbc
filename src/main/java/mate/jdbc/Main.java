package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final String MANUFACTURER_NAME = "Volvo";
    private static final String MANUFACTURER_COUNTRY = "Sweden";
    private static final String MANUFACTURER_NAME_TO_UPDATE = "BMW";
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(MANUFACTURER_NAME);
        manufacturer.setCountry(MANUFACTURER_COUNTRY);
        manufacturerDao.create(manufacturer);
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(manufacturerDao.get(6L));
        manufacturerDao.delete(19L);
        manufacturerDao.getAll().forEach(System.out::println);
        manufacturer.setName(MANUFACTURER_NAME_TO_UPDATE);
        manufacturerDao.update(manufacturer);

    }
}
