package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
        Manufacturer newManufacturer = new Manufacturer();
        newManufacturer.setName("BMW");
        newManufacturer.setCountry("Germany");
        Manufacturer createdManufacturer = manufacturerDao.create(newManufacturer);
        System.out.println(".create(): " + createdManufacturer + System.lineSeparator());

        Long createdManufacturerId = createdManufacturer.getId();
        Manufacturer gotManufacturerFromDb = manufacturerDao.get(createdManufacturerId).get();
        System.out.println(".get(): " + gotManufacturerFromDb + System.lineSeparator());

        System.out.println(".getAll(): ");
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(System.lineSeparator());

        boolean delete = manufacturerDao.delete(createdManufacturerId);
        System.out.println(".delete(): for " + createdManufacturer
                + " is " + delete + System.lineSeparator());

        System.out.println(".getAll() after .delete() operation: ");
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(System.lineSeparator());
    }
}
