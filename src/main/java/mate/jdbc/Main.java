package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer tesla = new Manufacturer();
        tesla.setName("Tesla");
        tesla.setCountry("United States");

        Manufacturer ford = new Manufacturer();
        ford.setName("Ford");
        ford.setCountry("United States");

        Manufacturer volkswagen = new Manufacturer();
        volkswagen.setName("Volkswagen");
        volkswagen.setCountry("Germany");

        System.out.println(manufacturerDao.create(tesla));
        System.out.println(manufacturerDao.create(ford));
        System.out.println(manufacturerDao.create(volkswagen));

        System.out.println(manufacturerDao.get(1L));
        System.out.println(manufacturerDao.get(2L));
        System.out.println(manufacturerDao.get(3L));

        manufacturerDao.getAll().forEach(System.out::println);

        ford.setName("Ford Mustang");
        System.out.println(manufacturerDao.update(ford));

        System.out.println(manufacturerDao.delete(ford.getId()));
    }
}
