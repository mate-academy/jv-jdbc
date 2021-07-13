package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer cokeManufacturer = new Manufacturer(1,"Coke", "USA");
        Manufacturer fordManufacturer = new Manufacturer(2,"Ford", "USA");
        Manufacturer appleManufacturer = new Manufacturer(3, "Apple", "USA");
        manufacturerDao.create(cokeManufacturer);
        manufacturerDao.create(fordManufacturer);
        manufacturerDao.create(appleManufacturer);
        Manufacturer samsungManufacturer = new Manufacturer(1,"Samsung", "China");
        manufacturerDao.update(samsungManufacturer);
        System.out.println(manufacturerDao.get(2L));
        manufacturerDao.delete(3L);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
