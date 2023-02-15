package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("LAZ");
        manufacturer.setCountry("Ukraine");
        manufacturerDao.create(manufacturer);
        System.out.println(manufacturerDao.get(4L).toString());
        System.out.println(manufacturerDao.get(5L).toString());
        manufacturer.setId(4L);
        manufacturer.setName("PAZ");
        manufacturerDao.update(manufacturer);
        System.out.println(manufacturerDao.get(4L).toString());
        manufacturerDao.delete(6L);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
