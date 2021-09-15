package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;

public class Main {
    private static final String PACKAGE_NAME = "mate.jdbc";
    private static final Injector injector = Injector.getInstance(PACKAGE_NAME);

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        System.out.println("Manufacturer List");
        System.out.println(manufacturerDao.getAll());
        System.out.println();

        System.out.println(manufacturerDao.get(2L));
        System.out.println(manufacturerDao.get(55L));

        System.out.println(manufacturerDao.getAll());

    }
}
