package mate.jdbc;

import mate.jdbc.dao.ManufacturersDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;
import java.util.List;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturersDao manufacturersDao
                = (ManufacturersDao) injector.getInstance(ManufacturersDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Audi");
        manufacturer.setCountry("Germany");
        manufacturersDao.create(manufacturer);

        List<Manufacturer> all = manufacturersDao.getAll();
        for (Manufacturer manufacturerElement : all) {
            System.out.println(manufacturerElement);
        }
    }
}
