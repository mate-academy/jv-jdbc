package mate.jdbc;

import java.util.List;
import mate.jdbc.lib.Injector;
import mate.jdbc.manufacturerdao.ManufacturerDao;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);
        List<Manufacturer> manufacturerDaoAll = manufacturerDao.getAll();
        manufacturerDaoAll.forEach(System.out::println);
        Manufacturer manufacturer = new Manufacturer("Jeep", "USA");
        manufacturerDao.create(manufacturer);
        manufacturerDaoAll.forEach(System.out::println);

    }
}
