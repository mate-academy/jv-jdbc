package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Long ID = 3L;
    private static final String NAME_TO_ADD = "Ford";
    private static final String COUNTRY_TO_ADD = "USA";
    private static final String NAME_TO_UPDATE = "Toyota";
    private static final String COUNTRY_TO_UPDATE = "Japan";
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(
                ManufacturerDao.class);
        System.out.println("The original data in table:");
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println();
        System.out.println("The data after adding a new manufacturer (Ford, USA):");
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(NAME_TO_ADD);
        manufacturer.setCountry(COUNTRY_TO_ADD);
        manufacturerDao.create(manufacturer);
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println();
        System.out.println("The manufacturer with id 3: ");
        Optional<Manufacturer> obtainedManufacturer = manufacturerDao.get(ID);
        System.out.println(obtainedManufacturer);
        System.out.println();
        System.out.println("The data in table after updating the manufacturer with id 3:");
        Manufacturer manufacturerToUpdate = new Manufacturer();
        manufacturerToUpdate.setId(ID);
        manufacturerToUpdate.setName(NAME_TO_UPDATE);
        manufacturerToUpdate.setCountry(COUNTRY_TO_UPDATE);
        manufacturerDao.update(manufacturerToUpdate);
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println();
        System.out.println("The data in table after deleting the manufacturer with id 3:");
        manufacturerDao.delete(ID);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
