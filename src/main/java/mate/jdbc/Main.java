package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer firstManufacturer = new Manufacturer("Volkswagen", "Germany");
        Manufacturer secondManufacturer = new Manufacturer("Toyota", "Japan");

        manufacturerDao.create(firstManufacturer);
        manufacturerDao.create(secondManufacturer);

        secondManufacturer.setId(11L);
        secondManufacturer.setName("Honda");

        manufacturerDao.update(secondManufacturer);
        manufacturerDao.delete(11L);
        System.out.println(manufacturerDao.get(10L));
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
