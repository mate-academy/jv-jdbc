package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.GenericDao;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        System.out.println("Data in DB: ");
        GenericDao<Manufacturer> manufacturerDao =
                (ManufacturerDao) injector.getInstance(GenericDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setCountry("Ukraine");
        manufacturer.setName("Jack");

        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        printManufacturers(manufacturers);

        System.out.println("Create: ");
        Manufacturer createdManufacturer = manufacturerDao.create(manufacturer);
        System.out.println(createdManufacturer);

        System.out.println("Get: ");
        Optional<Manufacturer> optionalManufacturer =
                manufacturerDao.get(createdManufacturer.getId());
        optionalManufacturer.ifPresent(System.out::println);

        System.out.println("Get all: ");
        manufacturers = manufacturerDao.getAll();
        printManufacturers(manufacturers);

        System.out.println("Update: ");
        createdManufacturer.setName("Bob");
        manufacturerDao.update(createdManufacturer);
        manufacturerDao.get(createdManufacturer.getId()).ifPresent(System.out::println);

        System.out.println("Delete: ");
        manufacturerDao.delete(createdManufacturer.getId());
        manufacturers = manufacturerDao.getAll();
        printManufacturers(manufacturers);
    }

    private static void printManufacturers(List<Manufacturer> manufacturers) {
        for (Manufacturer m : manufacturers) {
            System.out.println(m);
        }
    }
}
