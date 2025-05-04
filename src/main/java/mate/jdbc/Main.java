package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        //add manufacturer1
        Manufacturer bmw = new Manufacturer();
        bmw.setName("BMW");
        bmw.setCountry("Germany");
        //add manufacturer2
        Manufacturer dodge = new Manufacturer();
        dodge.setName("Dodge");
        dodge.setCountry("USA");
        // check 'create' method work
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        manufacturerDao.create(bmw);
        System.out.println("Add manufacturer1 success");
        manufacturerDao.create(dodge);
        System.out.println("Add manufacturer2 success");
        // check 'get' method work
        System.out.println(manufacturerDao.get(bmw.getId()));
        // check 'update' method work
        bmw.setName("BMW_GT");
        bmw.setCountry("Germany");
        manufacturerDao.update(bmw);
        // check 'delete' method work
        manufacturerDao.delete(dodge.getId());
        System.out.println("Delete manufacturer2 success");
        // check 'getAll' method work
        System.out.println(manufacturerDao.getAll());
    }
}
