package mate.jdbc;

import java.util.List;
import mate.jdbc.lib.Injector;
import mate.jdbc.lib.ManufacturerDao;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("VW");
        manufacturer.setCountry("Germany");
        //manufacturerDao.create(manufacturer);

        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        System.out.println("---Get All---");
        List<Manufacturer> allManufacturerDao = manufacturerDao.getAll();
        for (var man : allManufacturerDao) {
            System.out.println(man);
        }
        System.out.println("*************************");

        //System.out.println("---Get---");
        //System.out.println(manufacturerDao.get(7l));
        //System.out.println("*************************");

        //System.out.println("---Delete---");
        //System.out.println(manufacturerDao.delete(7l));
        //System.out.println("*************************");

        /*System.out.println("---Update---");
        Manufacturer newManufacturer = new Manufacturer();
        newManufacturer.setId(7l);
        newManufacturer.setName("Reno");
        newManufacturer.setCountry("France");
        System.out.println(manufacturerDao.update(newManufacturer));
        System.out.println("*************************");*/
    }
}
