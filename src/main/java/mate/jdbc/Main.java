package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);

        Manufacturer manufacturerJohn = new Manufacturer();
        manufacturerJohn.setName("John");
        manufacturerJohn.setCountry("Italy");
        System.out.println(manufacturerDao.create(manufacturerJohn));

        Manufacturer manufacturerBob = new Manufacturer();
        manufacturerBob.setName("Bob");
        manufacturerBob.setCountry("France");
        System.out.println(manufacturerDao.create(manufacturerBob));

        Manufacturer manufacturerIbrahim = new Manufacturer();
        manufacturerIbrahim.setName("Ibrahim");
        manufacturerIbrahim.setCountry("Netherlands");
        System.out.println(manufacturerDao.create(manufacturerIbrahim));

        System.out.println(manufacturerDao.get(3L));
        System.out.println(manufacturerDao.get(2L));
        System.out.println(manufacturerDao.get(1L));

        manufacturerJohn.setCountry("Finland");
        System.out.println(manufacturerDao.update(manufacturerJohn));

        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(manufacturerDao.delete(4L));

    }
}
