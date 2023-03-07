package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) INJECTOR.getInstance(ManufacturerDao.class);

        Manufacturer mercedes = new Manufacturer("Mercedes", "Germany");
        Manufacturer honda = new Manufacturer("Honda", "Japan");

        manufacturerDao.create(mercedes);
        manufacturerDao.create(honda);

        System.out.println(manufacturerDao.get(3L).get());
        System.out.println(manufacturerDao.get(4L).get());

        Manufacturer update = new Manufacturer();
        update.setId(3L);
        update.setName("MERCEDES");
        update.setCountry("GERMAN");
        manufacturerDao.update(update);

        manufacturerDao.delete(3L);

        manufacturerDao.getAll().forEach(System.out::println);

    }
}
