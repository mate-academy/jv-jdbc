package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer tesla = new Manufacturer(null, "Tesla", "USA");
        manufacturerDao.create(tesla);
        Manufacturer mercedes = new Manufacturer(null, "Mercedes", "Germany");
        manufacturerDao.create(mercedes);
        Manufacturer zaporozhets = new Manufacturer(null, "Zaporozhets", "Ukraine");
        manufacturerDao.create(zaporozhets);

        manufacturerDao.getAll().forEach(System.out::println);

        tesla.setCountry("Ukraine");
        manufacturerDao.update(tesla);

        System.out.println(manufacturerDao.get(tesla.getId()));

        manufacturerDao.delete(mercedes.getId());

        manufacturerDao.getAll().forEach(System.out::println);
    }
}
