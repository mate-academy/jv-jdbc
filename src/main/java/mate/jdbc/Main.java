package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Toyota");
        manufacturer.setCountry("Ukraine");
        manufacturer.setName("BMW");
         ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        manufacturerDao.delete(2L);
        manufacturerDao.delete(5L);
        manufacturerDao.create(manufacturer);
        System.out.println(manufacturerDao.get(4L));
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
