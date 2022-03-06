package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Mercedes");
        manufacturer.setCountry("Germany");
        Manufacturer savedManufacturer = manufacturerDao.create(manufacturer);
        System.out.println(savedManufacturer);
        System.out.println(manufacturerDao.get(1L));
        manufacturerDao.getAll().forEach(System.out::println);
        manufacturer.setId(3L);
        manufacturer.setName("Mercedes-Benz");
        manufacturerDao.update(manufacturer);
        manufacturerDao.delete(3L);
    }
}
