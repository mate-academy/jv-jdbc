package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);
        Manufacturer cokeManufacturer = new Manufacturer("Coke", "USA");
        Manufacturer fordManufacturer = new Manufacturer("Ford", "USA");
        Manufacturer appleManufacturer = new Manufacturer("Apple", "USA");
        manufacturerDao.create(cokeManufacturer);
        manufacturerDao.create(fordManufacturer);
        manufacturerDao.create(appleManufacturer);
        Manufacturer samsungManufacturer = new Manufacturer("Samsung", "China");
        samsungManufacturer.setId(20L);
        manufacturerDao.update(samsungManufacturer);
        manufacturerDao.get(18L);
        manufacturerDao.delete(2L);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
