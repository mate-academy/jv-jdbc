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
        Manufacturer bmwManufacturer = new Manufacturer();
        bmwManufacturer.setName("BMW");
        bmwManufacturer.setCountry("Poland");
        System.out.println(manufacturerDao.create(bmwManufacturer));
        // delete method
        System.out.println("Delete");
        System.out.println(manufacturerDao.delete(bmwManufacturer.getId()));
        // get method
        System.out.println("Get ");
        System.out.println(manufacturerDao.get(bmwManufacturer.getId()));
        // update method
        System.out.println("Update");
        Manufacturer renoManufacturer = new Manufacturer();
        renoManufacturer.setId(5L);
        renoManufacturer.setName("Reno");
        renoManufacturer.setCountry("France");
        System.out.println(manufacturerDao.update(renoManufacturer));
        // get all method
        System.out.println("Get all");
        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        for (Manufacturer manufacturer : allManufacturers) {
            System.out.println(manufacturer);
        }
    }
}
