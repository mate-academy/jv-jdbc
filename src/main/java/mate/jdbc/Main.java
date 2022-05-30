package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        //create method
        Manufacturer manufacturer1 = new Manufacturer();
        manufacturer1.setName("BMW");
        manufacturer1.setCountry("Poland");
        System.out.println(manufacturerDao.create(manufacturer1));
        // delete method
        System.out.println("Delete");
        System.out.println(manufacturerDao.delete(4L));
        // get method
        System.out.println("Get ");
        System.out.println(manufacturerDao.get(4L));
        // update method
        System.out.println("Update");
        Manufacturer manufacturer3 = new Manufacturer();
        manufacturer3.setId(5L);
        manufacturer3.setName("Reno");
        manufacturer3.setCountry("France");
        System.out.println(manufacturerDao.update(manufacturer3));
        // get all method
        System.out.println("Get all");
        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        for (Manufacturer manufacturer : allManufacturers) {
            System.out.println(manufacturer);
        }
    }
}
