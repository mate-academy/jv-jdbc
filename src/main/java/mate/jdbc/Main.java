package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;
import java.util.List;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("ZAZ");
        manufacturer.setCountry("Ukraine");
        manufacturerDao.create(manufacturer);

        List<Manufacturer> manufacturerList = manufacturerDao.getAll();
        manufacturerList
                .forEach((m) -> System.out.println(m.getId() + " " + m.getName() + " " + m.getCountry()));
    }
}
