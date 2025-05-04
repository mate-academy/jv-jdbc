package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer jaguar = initializeManufacturer("Jaguar", "England");
        Manufacturer peugeot = initializeManufacturer("Peugeot", "France");
        manufacturerDao.create(jaguar);
        manufacturerDao.create(peugeot);
        jaguar.setName("Range Rover");
        peugeot.setName("Renault");
        manufacturerDao.update(jaguar);
        manufacturerDao.update(peugeot);
        System.out.println("Updated jaguar and peugeot. List of all cars : "
                + manufacturerDao.getAll());
        manufacturerDao.delete(peugeot.getId());
        manufacturerDao.delete(jaguar.getId());
        System.out.println("List of cars after deletion"
                + manufacturerDao.getAll());
    }

    private static Manufacturer initializeManufacturer(String name, String country) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        return manufacturer;
    }
}
