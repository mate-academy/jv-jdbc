package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        manufacturerDao.create(new Manufacturer("BMW","Germany"));
        manufacturerDao.create(new Manufacturer("Mercedes","Germany"));
        manufacturerDao.create(new Manufacturer("Audi","Germany"));
        Manufacturer updateManufacturer = new Manufacturer("Harry","Potter");
        updateManufacturer.setId(1L);
        manufacturerDao.update(updateManufacturer);
        System.out.println(manufacturerDao.get(2L));
        manufacturerDao.delete(3L);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
