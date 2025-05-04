package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        final ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Porsche");
        manufacturer.setCountry("Germany");

        Manufacturer updateManufacturer = new Manufacturer();
        updateManufacturer.setId(3L);
        updateManufacturer.setName("Volvo");
        updateManufacturer.setCountry("Sweden");

        manufacturerDao.create(manufacturer);
        manufacturerDao.getAll();
        manufacturerDao.get(1L);
        manufacturerDao.update(updateManufacturer);
        manufacturerDao.delete(3L);
    }
}
