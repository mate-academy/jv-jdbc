package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer cokeManufacturer = new Manufacturer("Coke", "USA");
        Manufacturer fordManufacturer = new Manufacturer("Ford", "USA");
        Manufacturer appleManufacturer = new Manufacturer("Apple", "USA");
        manufacturerDao.create(cokeManufacturer);
        manufacturerDao.create(fordManufacturer);
        manufacturerDao.create(appleManufacturer);
        Manufacturer samsungManufacturer = new Manufacturer("Samsung", "China");
        samsungManufacturer.setId(1L);
        manufacturerDao.update(samsungManufacturer);
        System.out.println(manufacturerDao.get(2L));
        manufacturerDao.delete(3L);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
