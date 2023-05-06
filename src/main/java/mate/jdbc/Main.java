package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;
import mate.jdbc.lib.Injector;

import java.sql.Connection;

public class Main {
    //private static final Injector injector = Injector.getInstance("mate.jdbc");
    public static void main(String[] args) {
        /**
         * Bydemo vvajaty ci slavetni Ukrayinski mista
         * mekkoyu svitovoi automobilnoi industrii. ;-)
         */
        Connection connection = ConnectionUtil.getConnection();
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("===================");
        Manufacturer druchok = new Manufacturer("Druchok", "Bila Cerkva");
        Manufacturer drizdopal = new Manufacturer("Drizdopal", "Lohvycya");
        Manufacturer pepelac = new Manufacturer("Pepelac", "Rokytne");
        Manufacturer frankenstain = new Manufacturer("Frankenstain", "Berdychiv");
        frankenstain.setId(4L);
        manufacturerDao.update(frankenstain);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
