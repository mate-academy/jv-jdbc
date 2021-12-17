package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);

        Manufacturer mercedes = new Manufacturer("Mercedes Benz", "Germany");
        Manufacturer toyota = new Manufacturer("Toyota Motor", "Japan");
        toyota = manufacturerDao.create(toyota);
        mercedes = manufacturerDao.create(mercedes);
        System.out.println(manufacturerDao.getAll());
        System.out.println(manufacturerDao.get(1L));
        manufacturerDao.delete(1L);
        mercedes.setName("Super Mercedes Benz");
        manufacturerDao.update(mercedes);
        System.out.println(manufacturerDao.getAll());
    }
}
