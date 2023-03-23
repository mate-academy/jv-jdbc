package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer1 = new Manufacturer("BMW", "Germany");
        Manufacturer manufacturer2 = new Manufacturer("Toyota", "Japan");
        Manufacturer manufacturer3 = new Manufacturer("KIA", "South Korea");
        System.out.println(manufacturerDao.create(manufacturer1));
        System.out.println(manufacturerDao.create(manufacturer2));
        System.out.println(manufacturerDao.create(manufacturer3));
        System.out.println(manufacturerDao.get(2L));
        System.out.println(manufacturerDao.getAll());
        Manufacturer manufacturerUpdate = new Manufacturer(1L,"KIA", "North Korea");
        manufacturerUpdate.setId(3L);
        System.out.println(manufacturerDao.update(manufacturerUpdate));
        System.out.println(manufacturerDao.delete(1L));
    }
}
