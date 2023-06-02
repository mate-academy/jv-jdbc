package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer manufacturerToyota = new Manufacturer();
        manufacturerToyota.setName("Toyota");
        manufacturerToyota.setCountry("Japan");
        Manufacturer updateManufacturer = new Manufacturer();
        updateManufacturer.setId(1L);
        updateManufacturer.setName("Shkoda");
        updateManufacturer.setCountry("Czech Republic");
        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);
        manufacturerDao.update(updateManufacturer);
        manufacturerDao.create(manufacturerToyota);
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(manufacturerDao.delete(6L));
    }
}
