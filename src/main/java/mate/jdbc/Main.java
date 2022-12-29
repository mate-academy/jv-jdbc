package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Toyota");
        manufacturer.setCountry("Japan");
        Manufacturer toyotaManufacturer = manufacturerDao.create(manufacturer);
        Manufacturer toyotaManufacturerFromDB =
                manufacturerDao.get(toyotaManufacturer.getId()).get();
        toyotaManufacturerFromDB.setName("Honda");
        Manufacturer hondaManufacturer = manufacturerDao.update(toyotaManufacturerFromDB);
        manufacturerDao.delete(hondaManufacturer.getId());
        System.out.println(manufacturerDao.getAll());
    }
}
