package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer(3L, "VM", "Germany");
        System.out.println(manufacturerDao.create(manufacturer));
        System.out.println(manufacturerDao.update(manufacturer));
        List<Manufacturer> manufacturerAll = manufacturerDao.getAll();
        System.out.println(manufacturerDao.get(2L));
        System.out.println(manufacturerDao.delete(8L));
        for (Manufacturer manufacture : manufacturerAll) {
            System.out.println(manufacture);
        }
    }
}
