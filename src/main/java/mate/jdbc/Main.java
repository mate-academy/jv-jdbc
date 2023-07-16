package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static final ManufacturerDao manufacturerDao =
            (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

    public static void main(String[] args) {
        Manufacturer rollsRoyceManufacturer = new Manufacturer("Rolls-Royce", "UK");
        Manufacturer fordManufacturer = new Manufacturer("Ford", "USA");

        manufacturerDao.create(rollsRoyceManufacturer);
        manufacturerDao.create(fordManufacturer);
        System.out.println(manufacturerDao.getAll());

        rollsRoyceManufacturer.setCountry("China");
        manufacturerDao.update(rollsRoyceManufacturer);
        System.out.println(manufacturerDao.get(rollsRoyceManufacturer.getId()));

        manufacturerDao.delete(rollsRoyceManufacturer.getId());
    }
}
