package mate.jdbc;

import mate.jdbc.lib.Injector;
import mate.jdbc.lib.ManufacturerDao;
import mate.jdbc.lib.ManufacturerDaoImpl;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDaoImpl manufacturerDao
                = (ManufacturerDaoImpl) injector.getInstance(ManufacturerDao.class);
        // CREATE
        Manufacturer manufacturer1 = new Manufacturer();
        manufacturer1.setName("name_1");
        manufacturer1.setCountry("Germany");
        manufacturerDao.create(manufacturer1);
        Manufacturer manufacturer2 = new Manufacturer();
        manufacturer2.setName("name_2");
        manufacturer2.setCountry("Ukraine");
        manufacturerDao.create(manufacturer2);
        Manufacturer manufacturer3 = new Manufacturer();
        manufacturer3.setName("name_3");
        manufacturer3.setCountry("Italy");
        manufacturerDao.create(manufacturer3);
        // PRINT ALL
        System.out.println(manufacturerDao.getAll());
        // PRINT ONE MANUFACTURER
        System.out.println(manufacturerDao.get(manufacturer1.getId()));
        // UPDATE
        manufacturer1.setCountry("updateCountry");
        manufacturerDao.update(manufacturer1);
        // DELETE
        manufacturerDao.delete(manufacturer3.getId());
        System.out.println(manufacturerDao.getAll());
    }
}
