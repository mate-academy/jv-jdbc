package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Joe");
        manufacturer.setCountry("Cuba");
        //manufacturerDao.create(manufacturer);
        List<Manufacturer> all = manufacturerDao.getAll();
        for (Manufacturer m: all) {
            System.out.println(m.toString());
        }
        System.out.println(manufacturerDao.get(1L).toString());
    }
}
