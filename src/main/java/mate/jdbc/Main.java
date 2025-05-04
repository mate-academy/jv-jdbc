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
        manufacturer.setName("Toyota");
        manufacturer.setCountry("Ukraine");
        manufacturerDao.create(manufacturer);
        manufacturer.setName("BMV");
        manufacturerDao.update(manufacturer);
        System.out.println(manufacturerDao.get(5L).toString());
        manufacturerDao.delete(2L);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
