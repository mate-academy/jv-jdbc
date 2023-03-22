package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static final ManufacturerDaoImpl manufacturerDao
            = (ManufacturerDaoImpl) injector.getInstance(ManufacturerDao.class);

    public static void main(String[] args) {
        // create record
        Manufacturer manufacturerModel1 = new Manufacturer();
        manufacturerModel1.setName("user1");
        manufacturerModel1.setCountry("Ukraine");
        Manufacturer manufacturerModel2 = new Manufacturer();
        manufacturerModel2.setName("user2");
        manufacturerModel2.setCountry("United States of America");
        manufacturerDao.create(manufacturerModel1);
        manufacturerDao.create(manufacturerModel2);
        // getAll records
        System.out.println(manufacturerDao.getAll());
        // get record
        System.out.println(manufacturerDao.get(manufacturerModel1.getId()));
        // update record
        manufacturerModel1.setName("new user");
        manufacturerDao.update(manufacturerModel1);
        System.out.println(manufacturerDao.getAll());
        // delete record
        manufacturerDao.delete(manufacturerModel2.getId());
        System.out.println(manufacturerDao.getAll());
    }
}
