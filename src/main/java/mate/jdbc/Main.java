package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final String PACKAGE_NAME = "mate.jdbc";

    public static void main(String[] args) {
        Injector injector = Injector.getInstance(PACKAGE_NAME);
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        //testing:
        manufacturerDao.create(new Manufacturer("audi", "Germany"));
        manufacturerDao.create(new Manufacturer("infiniti", "Japan"));
        manufacturerDao.create(new Manufacturer("Ferrari", "Italy"));
        manufacturerDao.create(new Manufacturer("Cadillac", "USA"));
        manufacturerDao.delete(12L);
        System.out.println(manufacturerDao.get(15L).orElse(null));
        manufacturerDao.update(new Manufacturer(14L,"SEAT", "Spain"));
        System.out.println(manufacturerDao.getAll());
    }
}
