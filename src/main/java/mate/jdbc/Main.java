package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturerVW = new Manufacturer();
        manufacturerVW.setName("Volkswagen");
        manufacturerVW.setCountry("Germany");
        manufacturerDao.create(manufacturerVW);

        Manufacturer manufacturerLotus = new Manufacturer();
        manufacturerLotus.setId(19L);
        manufacturerLotus.setName("Lotus");
        manufacturerLotus.setCountry("Great Britain");
        manufacturerDao.create(manufacturerLotus);

        Manufacturer updatedManufacturer = new Manufacturer();
        updatedManufacturer.setId(19L);
        updatedManufacturer.setName("MG");
        updatedManufacturer.setCountry("Great Britain");

        manufacturerDao.update(updatedManufacturer);
        manufacturerDao.delete(20L);

        manufacturerDao.getAll().forEach(System.out::println);
    }
}
