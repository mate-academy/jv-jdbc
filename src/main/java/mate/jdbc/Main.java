package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                    .getInstance(ManufacturerDao.class);

        List<Manufacturer> all = manufacturerDao.getAll();
        for (Manufacturer manufacturer : all) {
            System.out.println(manufacturer);
        }
    }
}
