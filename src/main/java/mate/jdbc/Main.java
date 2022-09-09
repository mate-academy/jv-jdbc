package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer audi = new Manufacturer("Audi", "Germany");
        Manufacturer tesla = new Manufacturer("Tesla", "USA");
        Manufacturer toyota = new Manufacturer("Toyota", "Japan");
        tesla = manufacturerDao.create(tesla);
        toyota = manufacturerDao.create(toyota);
        audi = manufacturerDao.create(audi);
        System.out.println(manufacturerDao.get(tesla.getId()));
        manufacturerDao.delete(audi.getId());
        System.out.println(manufacturerDao.getAll());
    }
}
