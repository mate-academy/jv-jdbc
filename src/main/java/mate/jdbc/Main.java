package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        //initialising dao
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setCountry("USA");
        manufacturer.setName("Roman");
        //create test
        System.out.println(manufacturerDao.create(manufacturer));
        //get by id test
        System.out.println(manufacturerDao.get(8L));
        //getAll test
        for (Manufacturer elementManufacturer : manufacturerDao.getAll()) {
            System.out.println(elementManufacturer);
        }
        //update test
        Manufacturer testManufacturer = new Manufacturer();
        testManufacturer.setName("Petro");
        testManufacturer.setId(10L);
        testManufacturer.setCountry("USA");
        System.out.println(manufacturerDao.update(testManufacturer));
        //delete test
        System.out.println(manufacturerDao.delete(8L));
    }
}
