package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        manufacturerDao.getAll().forEach(System.out::println);
        Manufacturer volkswagen = new Manufacturer();
        volkswagen.setName("Volkswagen");
        volkswagen.setCountry("Germany");
        manufacturerDao.create(volkswagen);
        manufacturerDao.delete(volkswagen.getId());
        Manufacturer ford = new Manufacturer();
        ford.setId(7L);
        ford.setName("Ford");
        ford.setCountry("USA");
        manufacturerDao.update(ford);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
