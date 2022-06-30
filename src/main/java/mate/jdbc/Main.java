package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(8L);
        manufacturer.setName("Buick");
        manufacturer.setCountry("USA");
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        manufacturerDao.update(manufacturer);
        System.out.println(manufacturerDao.get(7L));
        manufacturer.setCountry("America");
        manufacturerDao.delete(8L);
        manufacturerDao.create(manufacturer);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
