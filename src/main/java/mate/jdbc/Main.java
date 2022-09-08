package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer bmw = new Manufacturer("BMW", "Germany");
        Manufacturer audi = new Manufacturer("Audi", "Germany");
        Manufacturer tesla = new Manufacturer("Tesla", "USA");
        Manufacturer toyota = new Manufacturer("Toyota", "Japan");
        manufacturerDao.create(tesla);
        manufacturerDao.create(toyota);
        manufacturerDao.create(audi);
        manufacturerDao.create(bmw);
        manufacturerDao.delete(2L);
        manufacturerDao.get(1L);
        System.out.println(manufacturerDao.getAll());
    }
}
