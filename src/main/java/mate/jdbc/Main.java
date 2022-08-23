package mate.jdbc;

import mate.jdbc.dao.ManufactureDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer mercedes = new Manufacturer();
        mercedes.setName("Mercedes-benz");
        mercedes.setCountry("Germany");
        Manufacturer ford = new Manufacturer();
        ford.setName("Ford");
        ford.setCountry("USA");
        ManufactureDao manufacturerDao = (ManufactureDao) injector
                .getInstance(ManufactureDao.class);
        manufacturerDao.create(mercedes);
        manufacturerDao.create(ford);
        manufacturerDao.getAll().forEach(System.out::println);
        ford.setCountry("Germany");
        manufacturerDao.update(ford);
        manufacturerDao.getAll().forEach(System.out::println);
        manufacturerDao.delete(ford.getId());
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
