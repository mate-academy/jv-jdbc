package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static ManufacturerDao manufacturerDao;

    public static void main(String[] args) {
        manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        //manufacturerDao = new ManufacturerDaoImpl();

        //createManufacturer("Wow", "Latvia"); // CREATE
        //updateManufacturerById(8L, "Scania", "Kiev"); // UPDATE
        //deleteManufacturerLineById(8L); // DELETE

        //getManufacturerById(1L);
        readAllLines();// READ
    }

    // CREATE CREATE CREATE CREATE CREATE CREATE CREATE CREATE CREATE CREATE CREATE
    // create manufacturer (add new line)
    private static void createManufacturer(Manufacturer manufacturer) {
        manufacturerDao.create(manufacturer);
    }

    // create manufacturer manually
    private static void createManufacturer(String name, String country) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        manufacturerDao.create(manufacturer);
    }

    // READ READ READ READ READ READ READ READ READ READ READ READ READ READ READ
    // display of all active positions
    private static void readAllLines() {
        manufacturerDao.getAll().forEach(System.out::println);
    }

    // display active position by its id
    private static void getManufacturerById(Long id) {
        System.out.println(manufacturerDao.getById(id));
    }

    // UPDATE UPDATE UPDATE UPDATE UPDATE UPDATE UPDATE UPDATE UPDATE UPDATE UPDATE
    // update by id
    private static void updateManufacturerById(Long id, String name, String country) {
        Manufacturer manufacturer = manufacturerDao.getById(id);
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        manufacturerDao.updateManufacturer(manufacturer);
    }

    // DELETE DELETE DELETE DELETE DELETE DELETE DELETE DELETE DELETE DELETE DELETE
    // delete manufacturer by id
    private static void deleteManufacturerLineById(Long id) {
        manufacturerDao.delete(id);
        //manufacturerDao.getAll().forEach(System.out::println);
    }

    // delete manufacturer
    private static void deleteManufacturer(Manufacturer manufacturer) {
        System.out.println(manufacturerDao.delete(manufacturer.getId()));
        //manufacturerDao.getAll().forEach(System.out::println);
    }
}
