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
        manufacturer.setName("AUDI");
        manufacturer.setCountry("GERMANY");
        manufacturerDao.create(manufacturer);

        manufacturerDao.getAll().forEach(System.out::println);

        Manufacturer newManufacturer = new Manufacturer();
        newManufacturer.setId(1L);
        newManufacturer.setName("Renault");
        newManufacturer.setCountry("FRANCE");
        System.out.println(manufacturerDao.update(newManufacturer));

        System.out.println(manufacturerDao.get(1L));
    }
}
