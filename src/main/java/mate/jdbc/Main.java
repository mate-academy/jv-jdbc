package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer bmw = new Manufacturer("BMW", "Germany");
        Manufacturer audi = new Manufacturer("Audi","Germany");
        manufacturerDao.create(bmw);
        manufacturerDao.delete(1L);
        manufacturerDao.delete(2L);
        manufacturerDao.create(audi);
        System.out.println("First - " + manufacturerDao.get(6L));
        System.out.println("All - " + manufacturerDao.getAll());

    }
}
