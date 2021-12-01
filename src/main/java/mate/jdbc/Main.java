package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        final ManufacturerDao manufacturerDao
                = (ManufacturerDaoImpl) injector.getInstance(ManufacturerDao.class);
        Manufacturer first = new Manufacturer();
        first.setName("first");
        first.setCountry("firstCountry");
        Manufacturer second = new Manufacturer();
        second.setName("second");
        second.setCountry("secondCountry");
        manufacturerDao.create(first);
        manufacturerDao.create(second);
        System.out.println(manufacturerDao.getAll());
        first.setName("noFirst");
        manufacturerDao.update(first);
        System.out.println(manufacturerDao.getAll());
        manufacturerDao.delete(1L);
        System.out.println(manufacturerDao.getAll());
    }
}
