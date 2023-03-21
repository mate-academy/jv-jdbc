package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.lib.Injector;

public class Main {

    private static final Injector injector = Injector.getInstance("mate.jdbc");
    public static void main(String[] args) {
        ManufacturerDaoImpl manufacturerDao = (ManufacturerDaoImpl) injector.getInstance(ManufacturerDao.class);

    }
}
