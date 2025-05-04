package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer bmw = new Manufacturer();
        bmw.setName("Bmw");
        bmw.setCountry("Germany");
        Manufacturer ford = new Manufacturer();
        ford.setName("Ford");
        ford.setCountry("USA");
        Manufacturer toyota = new Manufacturer();
        toyota.setName("Toyota");
        toyota.setCountry("Japan");

        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        manufacturerDao.create(bmw);
        System.out.println("Create manufacturer: " + bmw.getName());
        manufacturerDao.create(ford);
        System.out.println("Create manufacturer: " + ford.getName());
        manufacturerDao.create(toyota);
        System.out.println("Create manufacturer: " + toyota.getName());

        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        System.out.println("All manufacturer list:");
        for (Manufacturer manufacturer : allManufacturers) {
            System.out.println(manufacturer);
        }

        System.out.println("Update manufacturer: " + ford.getName());
        ford.setCountry("Japan");
        manufacturerDao.update(ford);
        System.out.println("Updated data :");
        allManufacturers = manufacturerDao.getAll();
        System.out.println("All manufacturer list:");
        for (Manufacturer manufacturer : allManufacturers) {
            System.out.println(manufacturer);
        }

        System.out.println("Delete manufacturer with id: " + toyota.getId());
        manufacturerDao.delete(toyota.getId());
        allManufacturers = manufacturerDao.getAll();
        System.out.println("All manufacturer list:");
        for (Manufacturer manufacturer : allManufacturers) {
            System.out.println(manufacturer);
        }
    }
}

