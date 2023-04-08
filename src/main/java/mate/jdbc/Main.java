package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.impl.ManufacturerDaoImpl;
import mate.jdbc.lib.Injector;
import mate.jdbc.util.ManufacturerUtil;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static final ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
    private static final ManufacturerUtil manufacturerUtil = new ManufacturerUtil();

    public static void main(String[] args) {
        ManufacturerUtil.getManufacturerByIndex(9L);
        ManufacturerUtil.deleteAllManufacturers();
        ManufacturerUtil.deleteManufacturer(2L);
        ManufacturerUtil.updateManufacturer("audi", "germany", 9L);
        ManufacturerUtil.createManufacturer("Toyota", "Japan");
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
