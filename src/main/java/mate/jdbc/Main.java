package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        System.out.println(manufacturerDao.get(2L));
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
