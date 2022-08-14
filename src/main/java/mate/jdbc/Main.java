package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        //Manufacturer manufacturer = new Manufacturer("VW", "Germany");
        // initialize field values using setters or constructor
        //manufacturerDao.create(manufacturer);
        System.out.println(manufacturerDao.get(3L));
        //manufacturerDao.getAll().forEach(System.out::println);
        // test other methods from ManufacturerDao
    }
}
