package mate.jdbc;

import mate.jdbc.db.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Jdbc {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static ManufacturerDao manufacturerDao;

    public static void main(String[] args) {
        manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer toyota = new Manufacturer();
        toyota.setName("Toyota");
        toyota.setCountry("Japan");
        Manufacturer bmw = new Manufacturer();
        bmw.setName("BMW");
        bmw.setCountry("Germany");
        Manufacturer ford = new Manufacturer();
        ford.setName("Ford");
        ford.setCountry("USA");

        manufacturerDao.create(bmw);
        manufacturerDao.create(ford);

        Manufacturer man = manufacturerDao.create(toyota);
        man.setName("Tata");
        man.setCountry("India");
        manufacturerDao.update(man);
        manufacturerDao.delete(man.getId());

        System.out.println(manufacturerDao.get(10L));

        manufacturerDao.getAll().forEach(System.out::println);
    }
}
