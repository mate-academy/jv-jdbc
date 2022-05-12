package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer("Audi", "Germany");
        Manufacturer manufacturerWithId = manufacturerDao.create(manufacturer);
        manufacturerDao.getAll().forEach(System.out::println);
        manufacturerWithId.setName("Toyota");
        manufacturerWithId.setCountry("Japan");
        manufacturerDao.update(manufacturerWithId);
        System.out.println(manufacturerDao.get(manufacturerWithId.getId()));
        manufacturerDao.delete(manufacturerWithId.getId());
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
