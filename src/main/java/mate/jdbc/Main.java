package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer secondManufacturer = new Manufacturer("BMW", "Germany");
        Manufacturer firstManufacturer = new Manufacturer("Porsche", "Germany");
        Manufacturer manufacturerWithId = manufacturerDao.create(firstManufacturer);
        manufacturerDao.create(secondManufacturer);
        manufacturerDao.getAll().forEach(System.out::println);
        manufacturerWithId.setName("Tesla");
        manufacturerWithId.setCountry("USA");
        manufacturerDao.update(manufacturerWithId);
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(manufacturerDao.get(manufacturerWithId.getId()));
        manufacturerDao.delete(manufacturerWithId.getId());
    }
}
