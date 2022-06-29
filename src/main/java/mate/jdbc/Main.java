package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        // getAll data from DataBase
        System.out.println("GetAll method testing");
        List<Manufacturer> manufacturerList = manufacturerDao.getAll();
        for (Manufacturer m : manufacturerList) {
            System.out.println(m);
        }
        // initialize field values using setters or constructor
        System.out.println("Initialize field values using setters or constructor");
        manufacturer.setName("TestManufacturer");
        manufacturer.setCountry("TestCountry");
        manufacturer = manufacturerDao.create(manufacturer);
        // test other methods from ManufacturerDao
        // get by id
        System.out.println("Get by id method testing");
        manufacturer = manufacturerDao.get(manufacturer.getId())
                .orElse(manufacturer);
        System.out.println(manufacturer);
        Manufacturer nonExistIdManufacturer = new Manufacturer();
        nonExistIdManufacturer.setId(5L);
        nonExistIdManufacturer = manufacturerDao.get(nonExistIdManufacturer.getId())
                                .orElse(nonExistIdManufacturer);
        System.out.println(nonExistIdManufacturer);
        // update
        System.out.println("Update method testing");
        manufacturer.setName("TestManufacturer1");
        manufacturer.setCountry("TestCountry1");
        manufacturer.setId(6L);
        System.out.println(manufacturerDao.update(manufacturer));

        manufacturer.setName("TestManufacturer2");
        manufacturer.setCountry("TestCountry2");
        manufacturer.setId(2L);
        System.out.println(manufacturerDao.update(manufacturer));

        //delete
        System.out.println("Delete method testing");

        System.out.println(manufacturerDao.delete(4L));

        // getAll data from DataBase
        manufacturerList = manufacturerDao.getAll();
        for (Manufacturer m : manufacturerList) {
            System.out.println(m);
        }
    }
}
