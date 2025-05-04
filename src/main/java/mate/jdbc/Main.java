package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static final ManufacturerDao manufacturerDao =
            (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

    public static void main(String[] args) {
        Manufacturer manufacturer1 = new Manufacturer();
        manufacturer1.setName("bmw");
        manufacturer1.setCountry("germany");

        Manufacturer manufacturer2 = new Manufacturer();
        manufacturer2.setName("lexus");
        manufacturer2.setCountry("japan");

        Manufacturer manufacturer3 = new Manufacturer();
        manufacturer3.setName("ford");
        manufacturer3.setCountry("usa");

        manufacturerDao.create(manufacturer1);
        manufacturerDao.create(manufacturer2);
        manufacturerDao.create(manufacturer3);

        manufacturerDao.getAll().forEach(System.out::println);

        manufacturer1.setName("mercedes");
        manufacturerDao.update(manufacturer1);
        manufacturerDao.delete(manufacturer2.getId());

        manufacturerDao.getAll().forEach(System.out::println);

    }
}
