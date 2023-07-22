package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Fiat");
        manufacturer.setCountry("France");
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
        Manufacturer createdManufacturer = manufacturerDao.create(manufacturer);
        System.out.println(createdManufacturer);
        manufacturer.setId(1L);
        manufacturer.setName("Renault");
        manufacturer.setCountry("France");
        manufacturerDao.update(manufacturer);
        manufacturerDao.get(1L);
        manufacturerDao.getAll();
        manufacturerDao.delete(1L);
    }
}
