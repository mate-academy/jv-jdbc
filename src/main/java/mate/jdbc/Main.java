package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer1 = new Manufacturer("name1", "country1");
        Manufacturer manufacturer2 = new Manufacturer("name2", "country2");
        Manufacturer manufacturer3 = new Manufacturer("name3", "country3");
        manufacturerDao.create(manufacturer1);
        manufacturerDao.create(manufacturer2);
        manufacturerDao.create(manufacturer3);
        System.out.println(manufacturerDao.getAll());
        System.out.println(manufacturerDao.get(manufacturer2.getId()));
        manufacturer3.setName("UpdatedName3");
        manufacturerDao.update(manufacturer3);
        manufacturerDao.delete(manufacturer1.getId());
        System.out.println(manufacturerDao.getAll());
        manufacturer1.setCountry("UpdatedCountry1");
        manufacturerDao.update(manufacturer1);
        System.out.println(manufacturerDao.get(manufacturer1.getId()));
    }
}
