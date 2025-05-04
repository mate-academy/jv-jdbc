package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);
        Manufacturer zaz = new Manufacturer("ZAZ", "Ukraine");
        Manufacturer kia = new Manufacturer("Kia","South Korea");
        manufacturerDao.create(zaz);
        manufacturerDao.delete(kia.getId());
        System.out.println(manufacturerDao.getAll());
    }
}
