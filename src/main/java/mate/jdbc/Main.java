package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        // initialize field values using setters or constructor
        manufacturerDao.create(new Manufacturer(null, "Opel", "Germany"));
        // test other methods from ManufacturerDao
        System.out.println(manufacturerDao.get(1L));
        manufacturerDao.update(new Manufacturer(1L, "Fiat", "Italy"));
        System.out.println(manufacturerDao.getAll());
        manufacturerDao.delete(9L);
    }
}
