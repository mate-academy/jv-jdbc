package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

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
        Manufacturer car = manufacturerDao.get(audi.getId()).get();
        System.out.println(car);
        Manufacturer carNext = new Manufacturer("Ford", "USA");
        manufacturerDao.create(carNext);
        carNext.setCountry("Canada");
        manufacturerDao.update(carNext);
        manufacturerDao.delete(carNext.getId());
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
