package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) INJECTOR
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer("Ira","Ukraine");
        manufacturerDao.create(manufacturer);
        System.out.println(manufacturerDao.get(1L));
        System.out.println(manufacturerDao.getAll());
        manufacturerDao.delete(2L);
        System.out.println(manufacturerDao.getAll());
        manufacturerDao.update(new Manufacturer(1L,"Bob","England"));
        System.out.println(manufacturerDao.getAll());
    }
}
