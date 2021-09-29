package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        System.out.println("All manufacturers:");
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        manufacturers.stream()
                .map(Manufacturer::toString)
                .forEach(System.out::println);
        System.out.println(System.lineSeparator()
                + "Manufacturer with id 2 ('null' if there no manufacturer with such id):");
        Optional<Manufacturer> optionalManufacturer = manufacturerDao.get(2L);
        System.out.println(optionalManufacturer.orElse(null));
        System.out.println(System.lineSeparator() + "Add new manufacturer:");
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Peugeot");
        manufacturer.setCountry("France");
        System.out.println(manufacturerDao.create(manufacturer));
        System.out.println(System.lineSeparator() + "Delete manufacturer with id 1:");
        System.out.println(manufacturerDao.delete(1L));
        System.out.println(System.lineSeparator() + "Update manufacturer with id 4:");
        Manufacturer newManufacturer = new Manufacturer(4L, "Honda", "Japan");
        System.out.println(manufacturerDao.update(newManufacturer));
        System.out.println(System.lineSeparator() + "All manufacturers:");
        manufacturers = manufacturerDao.getAll();
        manufacturers.stream()
                .map(Manufacturer::toString)
                .forEach(System.out::println);
    }
}
