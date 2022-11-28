package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer toyotaManufacturer = new Manufacturer();
        toyotaManufacturer.setName("Toyota");
        toyotaManufacturer.setCountry("Japan");
        Manufacturer nissanManufacturer = new Manufacturer();
        nissanManufacturer.setName("Nissan");
        nissanManufacturer.setCountry("Japan");
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        manufacturerDao.create(toyotaManufacturer);
        manufacturerDao.create(nissanManufacturer);
        manufacturerDao.get(1L);
        nissanManufacturer.setCountry("France");
        manufacturerDao.update(nissanManufacturer);
        manufacturerDao.delete(1L);
        manufacturerDao.getAll();
    }
}
