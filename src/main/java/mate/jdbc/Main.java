package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.service.DataProcessingException;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                    .getInstance(ManufacturerDao.class);
        Manufacturer newManufacturer = new Manufacturer("Tesla", "USA");
        manufacturerDao.create(newManufacturer);
        Manufacturer createdManufacturer = manufacturerDao.get(newManufacturer.getId())
                .orElseThrow(() -> new DataProcessingException("no such manufacturer"));
        createdManufacturer.setCountry("The US");
        manufacturerDao.update(createdManufacturer);
        manufacturerDao.delete(12L);
        manufacturerDao.getAll().stream()
                .forEach(System.out::println);
    }
}
