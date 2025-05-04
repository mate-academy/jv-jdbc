package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        manufacturerDaoImpl();
    }

    private static void manufacturerDaoImpl() {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer exitManufacturer = manufacturerDao.add(getManufacturer());
        exitManufacturer.setCountry("Italy");
        manufacturerDao.update(exitManufacturer);
        manufacturerDao.delete(1L);
        System.out.println(manufacturerDao.getAll());
    }

    private static Manufacturer getManufacturer() {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setCountry("Italy");
        manufacturer.setName("Ferrari");
        return manufacturer;
    }
}

