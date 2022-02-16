package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(5L);
        manufacturer.setName("Tesla");
        manufacturer.setCountry("United States");
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        manufacturerDao.update(manufacturer);
        System.out.println(manufacturerDao.get(7L));
        System.out.println(manufacturerDao.delete(manufacturer.getId()));
        manufacturerDao.create(manufacturer);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
