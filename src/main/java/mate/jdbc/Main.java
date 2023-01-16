package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        // test other methods from ManufacturerDao
        deleteAllManufacturesInDB(manufacturerDao);
        printAllManufacturesInDB(manufacturerDao);
        Manufacturer manufacturerOne
                = createManufacturer("Volkswagen", "Germany", manufacturerDao);
        Manufacturer manufacturerTwo
                = createManufacturer("BMV", "Germany", manufacturerDao);
        Manufacturer manufacturerThree
                = createManufacturer("Toyota", "Japan", manufacturerDao);
        printAllManufacturesInDB(manufacturerDao);
        getManufacturer(manufacturerDao, manufacturerTwo.getId());
        updateManufacturer(manufacturerDao, manufacturerOne);
        printAllManufacturesInDB(manufacturerDao);
        deleteManufacturers(manufacturerDao, manufacturerOne.getId());
        deleteManufacturers(manufacturerDao, 56L);
        printAllManufacturesInDB(manufacturerDao);
        getManufacturer(manufacturerDao, 53L);

    }

    private static Manufacturer createManufacturer(String name,
                                                   String country,
                                                   ManufacturerDao manufacturerDao) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        System.out.println(manufacturerDao.create(manufacturer));
        return manufacturer;
    }

    private static void printAllManufacturesInDB(ManufacturerDao manufacturerDao) {
        System.out.println(manufacturerDao.getAll());
    }

    private static void deleteAllManufacturesInDB(ManufacturerDao manufacturerDao) {
        for (Manufacturer manufacturer : manufacturerDao.getAll()) {
            manufacturerDao.delete(manufacturer.getId());
        }
    }

    private static void getManufacturer(ManufacturerDao manufacturerDao, Long id) {
        System.out.println(manufacturerDao.get(id));
    }

    private static void deleteManufacturers(ManufacturerDao manufacturerDao, Long id) {
        System.out.println("Manufacturer id = " + id + " deleted  :" + manufacturerDao.delete(id));
    }

    private static void updateManufacturer(ManufacturerDao manufacturerDao,
                                           Manufacturer manufacturer) {
        manufacturer.setName("Fiat");
        manufacturer.setCountry("France");
        manufacturerDao.update(manufacturer);
    }
}
