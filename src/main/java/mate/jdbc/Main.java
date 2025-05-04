package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        final ManufacturerDaoImpl manufacturerDao
                = (ManufacturerDaoImpl) injector.getInstance(ManufacturerDao.class);
        // create
        Manufacturer manufacturerModel1 = new Manufacturer();
        manufacturerModel1.setName("name1");
        manufacturerModel1.setCountry("Ukraine");
        Manufacturer manufacturerModel2 = new Manufacturer();
        manufacturerModel2.setName("name2");
        manufacturerModel2.setCountry("United Kingdom");
        manufacturerDao.create(manufacturerModel1);
        manufacturerDao.create(manufacturerModel2);
        // getAll
        System.out.println(manufacturerDao.getAll());
        // get
        System.out.println(manufacturerDao.get(manufacturerModel1.getId()));
        // update
        manufacturerModel1.setName("updated name");
        manufacturerDao.update(manufacturerModel1);
        System.out.println(manufacturerDao.getAll());
        // delete
        manufacturerDao.delete(manufacturerModel2.getId());
        System.out.println(manufacturerDao.getAll());
    }
}
