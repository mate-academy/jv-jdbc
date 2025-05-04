package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer mazda = new Manufacturer();
        mazda.setName("Mazda");
        mazda.setCountry("Germany");
        Manufacturer peugeot = new Manufacturer();
        peugeot.setName("Peugeot");
        peugeot.setCountry("Italy");

        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        System.out.println(manufacturerDao.create(mazda));
        System.out.println(manufacturerDao.create(peugeot));

        System.out.println(manufacturerDao.delete(2L));

        Manufacturer updateManufacturer = new Manufacturer();
        updateManufacturer.setId(4L);
        updateManufacturer.setName("Mazda");
        updateManufacturer.setCountry("Japan");
        manufacturerDao.update(updateManufacturer);

        System.out.println(manufacturerDao.get(3L));

        manufacturerDao.getAll().forEach(System.out::println);
    }
}
