package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        //add manufacturer1
        Manufacturer manufacturer1 = new Manufacturer();
        manufacturer1.setName("Bob");
        manufacturer1.setCountry("USA");
        //add manufacturer2
        Manufacturer manufacturer2 = new Manufacturer();
        manufacturer2.setName("Alice");
        manufacturer2.setCountry("USA");
        // check 'create' method work
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        manufacturerDao.create(manufacturer1);
        System.out.println("Add manufacturer1 success");
        manufacturerDao.create(manufacturer2);
        System.out.println("Add manufacturer2 success");
        // check 'get' method work
        System.out.println(manufacturerDao.get(manufacturer1.getId()));
        // check 'update' method work
        manufacturer1.setName("Chack");
        manufacturer1.setCountry("Germany");
        manufacturerDao.update(manufacturer1);
        // check 'delete' method work
        manufacturerDao.delete(manufacturer2.getId());
        System.out.println("Delete manufacturer2 success");
        // check 'getAll' method work
        System.out.println(manufacturerDao.getAll());
    }
}
