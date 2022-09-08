package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final String PACKAGE_NAME = "mate.jdbc";
    private static final Injector injector = Injector.getInstance(PACKAGE_NAME);

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        //testing:
        Manufacturer audi =
                manufacturerDao.create(new Manufacturer("audi", "Germany"));
        Manufacturer infiniti =
                manufacturerDao.create(new Manufacturer("infiniti", "Japan"));
        Manufacturer ferrari =
                manufacturerDao.create(new Manufacturer("Ferrari", "Italy"));
        Manufacturer cadillac =
                manufacturerDao.create(new Manufacturer("Cadillac", "USA"));
        manufacturerDao.delete(cadillac.getId());
        System.out.println(manufacturerDao.get(infiniti.getId()).orElse(null));
        manufacturerDao.update(new Manufacturer(ferrari.getId(),"SEAT", "Spain"));
        System.out.println(manufacturerDao.getAll());
    }
}
