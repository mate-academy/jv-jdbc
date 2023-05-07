package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);

        /*
         * Bydemo vvajaty ci slavetni Ukrayinski mista
         * mekkoyu svitovoi automobilnoi industrii. ;-)
         */

        Manufacturer druchok = new Manufacturer("Druchok", "Bila Cerkva");
        Manufacturer drizdopal = new Manufacturer("Drizdopal", "Lohvycya");
        Manufacturer pepelac = new Manufacturer("Pepelac", "Rokytne");
        Manufacturer frankenstain = new Manufacturer("Frankenstain", "Berdychiv");

        manufacturerDao.create(druchok);
        manufacturerDao.create(drizdopal);
        manufacturerDao.create(pepelac);
        manufacturerDao.create(frankenstain);
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("===========================");

        manufacturerDao.update(new Manufacturer(2L, "Shkarlypa", "Polonyna"));
        manufacturerDao.delete(3L);
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("===========================");

        System.out.println(manufacturerDao.get(4L));
    }
}
