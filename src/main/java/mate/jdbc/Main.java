package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer1 = new Manufacturer("BMW", "Germany");
        Manufacturer manufacturer2 = new Manufacturer("BMW", "Germany");
        manufacturer2.setId(1);
        manufacturerDao.create(manufacturer1);
        manufacturerDao.update(manufacturer2);
        manufacturerDao.delete(1L);
        System.out.println(manufacturerDao.getAll());
        System.out.println(manufacturerDao.get(2L));
    }
}
