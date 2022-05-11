package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer(null, "AUDI", "GERMANY");
        manufacturerDao.getAll().forEach(System.out::println);
        manufacturerDao.create(manufacturer);
        manufacturer.setName("TOYOTA");
        manufacturer.setCountry("JAPAN");
        manufacturerDao.update(manufacturer);
        manufacturerDao.get(manufacturer.getId());
        manufacturerDao.delete(manufacturer.getId());
    }
}
