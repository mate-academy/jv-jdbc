package mate.jdbc;

import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.service.ManufacturerDao;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static final ManufacturerDao manufacturerDao =
            (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

    public static void main(String[] args) {
        Manufacturer audi = new Manufacturer("Audi", "Germany");
        Manufacturer mercedes = new Manufacturer("mercedes", "Germany");
        manufacturerDao.create(mercedes);
        manufacturerDao.create(audi);
        manufacturerDao.getAll().forEach(System.out::println);
        Manufacturer car = manufacturerDao.get(6L).get();
        System.out.println(car);
        Manufacturer carNext = new Manufacturer(25L, "Ford", "USA");
        manufacturerDao.update(carNext);
        manufacturerDao.delete(1L);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
