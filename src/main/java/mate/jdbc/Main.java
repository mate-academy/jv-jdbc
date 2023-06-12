package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        //create first car
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("mercedes-benz");
        manufacturer.setCountry("germany");
        System.out.println(manufacturerDao.create(manufacturer));
        //create second car
        Manufacturer manufacturer2 = new Manufacturer();
        manufacturer2.setName("fiat");
        manufacturer2.setCountry("italy");
        System.out.println(manufacturerDao.create(manufacturer2));
        //get all manufacturers
        manufacturerDao.getAll().forEach(System.out::println);
        //update name for first car
        manufacturer.setName("volkswagen");
        manufacturerDao.update(manufacturer);
        //get all manufacturers
        manufacturerDao.getAll().forEach(System.out::println);
        //get manufacturer by id
        System.out.println(manufacturerDao.get(manufacturer.getId()));
        //delete manufacturer by id
        System.out.println(manufacturerDao.delete(manufacturer.getId()));
        //get all manufacturers
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
