package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);

        manufacturerDao.create(new Manufacturer("Lamborghini", "Italy"));
        manufacturerDao.create(new Manufacturer("Ferrarii", "Italy"));
        manufacturerDao.create(new Manufacturer("Porsche", "Germany"));

        System.out.println("At first all manufacturers are:");
        manufacturerDao.getAll().forEach(System.out::println);

        Optional<Manufacturer> optionalManufacturer = manufacturerDao.get(34L);
        optionalManufacturer.get().setName("Ferrari");
        manufacturerDao.update(optionalManufacturer.get());

        System.out.println("After Ferrari updating manufacturers are:");
        manufacturerDao.getAll().forEach(System.out::println);

        manufacturerDao.delete(2L);

        System.out.println("After Ferrari deleting manufacturers are:");
        manufacturerDao.getAll().forEach(System.out::println);

        System.out.println("The manufacturer with index = 2 is:\n" + manufacturerDao.get(2L));
    }
}
