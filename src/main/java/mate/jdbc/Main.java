package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer firstManufacturer = new Manufacturer();
        firstManufacturer.setName("Vito");
        firstManufacturer.setCountry("USA");
        System.out.println(manufacturerDao.create(firstManufacturer));
        Manufacturer secondManufacturer = new Manufacturer();
        secondManufacturer.setName("Bogdan");
        secondManufacturer.setCountry("Ukraine");
        manufacturerDao.create(secondManufacturer);
        System.out.println(manufacturerDao.get(5L));
        manufacturerDao.delete(secondManufacturer.getId());
        System.out.println(manufacturerDao.getAll());
    }
}
