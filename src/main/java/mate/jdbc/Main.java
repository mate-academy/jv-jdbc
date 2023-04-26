package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static ManufacturerDao manufacturerDao =
            (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

    public static void main(String[] args) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(6L);
        manufacturer.setName("Ford");
        manufacturer.setCountry("USA(2)");
        System.out.println(manufacturerDao.get(5L));
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(manufacturerDao.update(manufacturer));
        System.out.println(manufacturerDao.delete(manufacturer.getId()));
    }
}
