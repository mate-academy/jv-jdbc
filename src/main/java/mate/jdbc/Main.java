package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Mazda");
        manufacturer.setCountry("Japan");
        manufacturerDao.create(manufacturer);
        manufacturerDao.get(7L);
        manufacturer.setName("Ford");
        manufacturer.setCountry("USA");
        manufacturerDao.update(manufacturer);
        manufacturerDao.delete(7L);
        manufacturerDao.getAll();
    }
}
