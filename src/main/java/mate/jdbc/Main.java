package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        final ManufacturerDao manufacturerDao =
                (ManufacturerDaoImpl) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer1 = new Manufacturer();
        manufacturer1.setName("bmw");
        manufacturer1.setCountry("germany");

        Manufacturer manufacturer2 = new Manufacturer();
        manufacturer2.setName("audi");
        manufacturer2.setCountry("germany");

        Manufacturer manufacturer3 = new Manufacturer();
        manufacturer3.setName("porsche");
        manufacturer3.setCountry("germany");

        manufacturerDao.create(manufacturer1);
        manufacturerDao.create(manufacturer2);
        manufacturerDao.create(manufacturer3);
        System.out.println(manufacturerDao.getAll());

        manufacturer2.setName("ferrari");
        manufacturer2.setCountry("italy");
        manufacturerDao.update(manufacturer2);
        System.out.println(manufacturerDao.getAll());

        System.out.println(manufacturerDao.get(manufacturer3.getId()));

        manufacturerDao.delete(manufacturer3.getId());
        System.out.println(manufacturerDao.getAll());
    }
}
