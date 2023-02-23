package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer("toyota", "japan");
        Manufacturer manufacturer2 = new Manufacturer("lexus", "japan");
        Manufacturer manufacturer3 = new Manufacturer("kia", "korea");
        // initialize field values using setters or constructor
        manufacturerDao.create(manufacturer);
        manufacturerDao.create(manufacturer2);
        manufacturerDao.create(manufacturer3);
        System.out.println(manufacturerDao.getAll());

        manufacturerDao.delete(2L);
        manufacturer.setCountry("ukraine");
        manufacturerDao.update(manufacturer);

        manufacturerDao.update(new Manufacturer("toyota", "japan1"));
        System.out.println(manufacturerDao.get(2L));
        System.out.println(manufacturerDao.get(3L));
        // test other methods from ManufacturerDao
    }
}
