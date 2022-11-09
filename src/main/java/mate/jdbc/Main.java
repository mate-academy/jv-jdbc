package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Toyota");
        manufacturer.setCountry("Japan");
        Manufacturer manufacturer2 = new Manufacturer();
        manufacturer2.setName("Ford");
        manufacturer2.setCountry("USA");
        manufacturer2.setId(1L);
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        manufacturerDao.create(manufacturer);
        manufacturerDao.get(1L);
        manufacturerDao.update(manufacturer2);
        manufacturerDao.getAll();
        manufacturerDao.delete(1L);
    }
}
