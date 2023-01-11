package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer manufacturer1 = new Manufacturer();
        Manufacturer manufacturer2 = new Manufacturer();
        manufacturer1.setCountry("Ukraine");
        manufacturer1.setName("VEPR");
        manufacturer2.setCountry("USA");
        manufacturer2.setName("HIMARS");
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        manufacturerDao.create(manufacturer1);
        manufacturerDao.create(manufacturer2);
        System.out.println(manufacturerDao.get(76L));
        System.out.println(manufacturerDao.getAll());
        manufacturerDao.update(manufacturer2);
        manufacturerDao.delete(76L);
        System.out.println(manufacturerDao.getAll());
    }
}
