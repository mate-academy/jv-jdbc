package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturerHonda = new Manufacturer("Honda", "Japan");
        Manufacturer manufacturerSuzuki = new Manufacturer("Suzuki", "Japan");
        Manufacturer manufacturerBohdan = new Manufacturer("Bohdan", "Ukraine");

        Manufacturer newManufacturer = new Manufacturer(10L,"Mercedes", "Germany");

        manufacturerDao.create(manufacturerHonda);
        manufacturerDao.create(manufacturerSuzuki);
        manufacturerDao.create(manufacturerBohdan);

        manufacturerDao.update(newManufacturer);

        manufacturerDao.delete(Long.valueOf(2));

        System.out.println("Manufacturer with id #1 is: " + manufacturerDao.get(Long.valueOf(1)));

        manufacturerDao.getAll().forEach(System.out::println);
    }
}

