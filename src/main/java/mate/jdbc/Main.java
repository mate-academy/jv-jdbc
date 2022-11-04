package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao.impl");

    public static void main(String[] args) {
        ManufacturerDao manufacturersDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        List<Manufacturer> allManufacturers = manufacturersDao.getAll();

        for (Manufacturer manufacturer : allManufacturers) {
            System.out.println(manufacturer);
        }

        boolean delete = manufacturersDao.delete(allManufacturers.get(0).getId());
        manufacturersDao.update(new Manufacturer(2L, "Lambo", "Italy"));
        manufacturersDao.create(new Manufacturer("Audi", "Germany"));
        System.out.println(manufacturersDao.get(2L));
    }
}
