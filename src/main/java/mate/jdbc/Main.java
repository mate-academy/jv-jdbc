package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturerBmw = new Manufacturer("BMW", "Germany");
        Manufacturer manufacturerAudi = new Manufacturer("Audi", "Germany");
        manufacturerDao.create(manufacturerBmw);
        manufacturerDao.create(manufacturerAudi);
        manufacturerDao.get(manufacturerBmw.getId());
        manufacturerBmw.setCountry("Sweden");
        manufacturerDao.update(manufacturerBmw);
        System.out.println(manufacturerDao.getAll());
        manufacturerDao.delete(manufacturerBmw.getId());
    }
}
