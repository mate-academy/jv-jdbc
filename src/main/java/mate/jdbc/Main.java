package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {

    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer1 = new Manufacturer("123", "USA");
        manufacturerDao.create(manufacturer1);
        System.out.println(manufacturerDao.get(2L));
        for (Manufacturer manufacturer : manufacturerDao.getAll()) {
            System.out.println(manufacturer);
        }
        Manufacturer manufacturer2 = new Manufacturer("567", "Canada");
        manufacturer2.setId(2L);
        System.out.println(manufacturerDao.get(2L));
        manufacturerDao.delete(2L);
        System.out.println(manufacturerDao.get(2L));
    }
}
