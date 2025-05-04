package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturerBmw = new Manufacturer("BMW", "Germany");
        Manufacturer manufacturerHonda = new Manufacturer("Honda", "Japan");
        manufacturerDao.create(manufacturerBmw);
        manufacturerDao.create(manufacturerHonda);
        manufacturerHonda.setCountry("USA");
        manufacturerDao.update(manufacturerHonda);
        manufacturerDao.delete(manufacturerBmw.getId());
        System.out.println(manufacturerDao.getAll());
        System.out.println(manufacturerDao.get(manufacturerHonda.getId()));
    }
}
