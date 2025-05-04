package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer lamboManufacturer = new Manufacturer();
        lamboManufacturer.setName("Lamborgini");
        lamboManufacturer.setCountry("Italy");
        Manufacturer audiManufacturer = new Manufacturer();
        audiManufacturer.setName("Audi");
        audiManufacturer.setCountry("Germany");
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        manufacturerDao.create(lamboManufacturer);
        manufacturerDao.create(audiManufacturer);
        manufacturerDao.get(1L);
        audiManufacturer.setCountry("Austria");
        manufacturerDao.update(audiManufacturer);
        manufacturerDao.delete(1L);
        manufacturerDao.getAll();

    }
}
