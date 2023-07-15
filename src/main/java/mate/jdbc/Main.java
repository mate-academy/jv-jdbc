package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        manufacturerDao.getAll();
        manufacturerDao.get(5L);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Meizu");
        manufacturer.setCountry("China");
        manufacturerDao.create(manufacturer);
        Manufacturer manufacturer1 = new Manufacturer();
        manufacturer1.setId(8L);
        manufacturer1.setName("CAT");
        manufacturer1.setCountry("USA");
        manufacturerDao.update(manufacturer1);
        manufacturerDao.delete(15L);
    }
}
