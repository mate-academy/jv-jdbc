package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;

public class Main {
    private static final Injector injector
            = Injector.getInstance(ManufacturerDao.class.getPackageName());

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
    }
}
