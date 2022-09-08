package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("BMW");
        manufacturer.setCountry("GERMANY");
        manufacturerDao.create(manufacturer);
        System.out.println(manufacturerDao.get(3L));
        Manufacturer updateManufacturer = new Manufacturer();
        updateManufacturer.setId(1L);
        updateManufacturer.setName("ZAZ");
        updateManufacturer.setCountry("UKRAINE");
        manufacturerDao.update(updateManufacturer);
        manufacturerDao.delete(8L);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
