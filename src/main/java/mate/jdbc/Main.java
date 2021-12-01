package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer ford = new Manufacturer("Ford", "USA");
        Manufacturer lamborghini = new Manufacturer("Lamborghini", "Italy");
        Manufacturer volkswagen = new Manufacturer("Volkswagen", "Germany");
        manufacturerDao.create(ford);
        manufacturerDao.create(lamborghini);
        manufacturerDao.create(volkswagen);
        System.out.println(manufacturerDao.getAll());
        System.out.println(manufacturerDao.get(1L));
        lamborghini.setCountry("Ukraine");
        manufacturerDao.update(lamborghini);
        manufacturerDao.delete(1L);
        System.out.println(manufacturerDao.getAll());
    }
}
