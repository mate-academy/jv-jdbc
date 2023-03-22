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
        Manufacturer manufacturer1 = new Manufacturer("bmw","germany");
        Manufacturer manufacturer2 = new Manufacturer("audi", "germany");
        Manufacturer manufacturer3 = new Manufacturer("porsche", "germany");

        manufacturerDao.create(manufacturer1);
        manufacturerDao.create(manufacturer2);
        manufacturerDao.create(manufacturer3);
        System.out.println("Creating new objects in our database");
        System.out.println(manufacturerDao.getAll());

        manufacturer2.setName("ferrari");
        manufacturer2.setCountry("italy");
        manufacturerDao.update(manufacturer2);
        System.out.println("Updating object in our database");
        System.out.println(manufacturerDao.getAll());

        System.out.println(manufacturerDao.get(manufacturer3.getId()));

        manufacturerDao.delete(manufacturer3.getId());
        System.out.println("Deleting object in our database");
        System.out.println(manufacturerDao.getAll());
    }
}
