package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {

    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);

        // Delete record from manufacturer table
        System.out.println(manufacturerDao.delete(6L) ? "Record deleted" : "Record not found");
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        manufacturers.forEach(System.out::println);

        // Get record from manufacturer table
        System.out.println("Got manufacturer by id:" + manufacturerDao.get(7L));

        //Update record in manufacturer table
        Manufacturer updateManufacturer = new Manufacturer(11L,"Lada", "USSR");
        Manufacturer updatedManufacturer = manufacturerDao.update(updateManufacturer);
        System.out.println("Updated manufacturer: " + updatedManufacturer);

        //Add new record to manufacturer table
        Manufacturer newManufacturer = new Manufacturer("Cherry3", "China");
        Manufacturer addedManufacturer = manufacturerDao.create(newManufacturer);
        System.out.println("Added manufacturer: " + addedManufacturer);

        //Get All records of Manufacturer
        manufacturers = manufacturerDao.getAll();
        manufacturers.forEach(System.out::println);

    }
}
