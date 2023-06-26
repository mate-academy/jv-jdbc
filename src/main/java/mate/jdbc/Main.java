package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static final ManufacturerDao manufacturerDao =
            (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

    public static void main(String[] args) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("mercedes");
        manufacturer.setCountry("germany");

        Manufacturer manufacturer2 = new Manufacturer();
        manufacturer2.setName("toyota");
        manufacturer2.setCountry("japan");

        manufacturerDao.create(manufacturer);
        manufacturerDao.create(manufacturer2);

        manufacturerDao.getAll().forEach(System.out::println);

        manufacturer.setName("bmw");
        manufacturerDao.update(manufacturer);
        manufacturerDao.getAll().forEach(System.out::println);

        System.out.println(manufacturerDao.get(manufacturer.getId()));
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
