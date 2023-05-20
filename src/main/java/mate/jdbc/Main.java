package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Hyundai");
        manufacturer.setCountry("Ukraine");

        Manufacturer updatedManufacturer = new Manufacturer();
        updatedManufacturer.setId(1L);
        updatedManufacturer.setName("Audi");
        updatedManufacturer.setCountry("Germany");

        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        manufacturerDao.create(manufacturer);
        manufacturerDao.update(updatedManufacturer);
        manufacturerDao.getAll()
                .forEach(System.out::println);
        System.out.println(manufacturerDao.get(2L));
        System.out.println(manufacturerDao.delete(3L));
    }
}
