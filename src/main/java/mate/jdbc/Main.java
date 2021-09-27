package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.service.DataProcessingException;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static Manufacturer manufacturer;

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                    .getInstance(ManufacturerDao.class);
        manufacturer = new Manufacturer("Tesla", "USA");
        manufacturerDao.create(manufacturer);
        manufacturer = manufacturerDao.get((long) 11)
                .orElseThrow(() -> new DataProcessingException("no such manufacturer"));
        manufacturer.setCountry("The US");
        manufacturerDao.update(manufacturer);
        manufacturerDao.delete((long) 12);
        manufacturerDao.getAll().stream()
                .forEach(System.out::println);
    }
}
