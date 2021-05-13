package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setCountry("Japan");
        manufacturer.setName("Nissan");
        manufacturer.setId(7L);
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        System.out.println(manufacturerDao.update(manufacturer));
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(manufacturerDao.get(1L));
        manufacturerDao.delete(3L);
        manufacturerDao.delete(4L);
    }
}
