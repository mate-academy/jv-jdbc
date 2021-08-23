package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        createManufacturers(manufacturerDao);

        // test other methods from ManufacturerDao
        System.out.println(manufacturerDao.delete(15L));

        Optional<Manufacturer> optionalManufacturer = manufacturerDao.get(4L);
        optionalManufacturer.ifPresent(System.out::println);
        if (optionalManufacturer.isPresent()) {
            Manufacturer manufacturer = optionalManufacturer.get();
            manufacturer.setCountry("USA");
            System.out.println(manufacturerDao.update(manufacturer));
        }

        Manufacturer manufacturerToUpdate = new Manufacturer();
        manufacturerToUpdate.setId(6L);
        manufacturerToUpdate.setName("Zaz");
        manufacturerToUpdate.setCountry("Ukraine");
        System.out.println(manufacturerDao.update(manufacturerToUpdate));

        System.out.println("=======================");
        manufacturerDao.getAll().forEach(System.out::println);
    }

    private static void createManufacturers(ManufacturerDao manufacturerDao) {
        Manufacturer manufacturerAudi = new Manufacturer();
        manufacturerAudi.setName("Audi");
        manufacturerAudi.setCountry("Germany");
        manufacturerDao.create(manufacturerAudi);

        Manufacturer manufacturerPorsche = new Manufacturer();
        manufacturerPorsche.setName("Porsche");
        manufacturerPorsche.setCountry("Germany");
        manufacturerDao.create(manufacturerPorsche);

        Manufacturer manufacturerToyota = new Manufacturer();
        manufacturerToyota.setName("Toyota");
        manufacturerToyota.setCountry("Japan");
        manufacturerDao.create(manufacturerToyota);

        Manufacturer manufacturerFord = new Manufacturer();
        manufacturerFord.setName("Ford");
        manufacturerFord.setCountry("USA");
        Manufacturer savedManufacturer = manufacturerDao.create(manufacturerFord);
        System.out.println(savedManufacturer);
        System.out.println(manufacturerDao.delete(savedManufacturer.getId()));
    }
}
