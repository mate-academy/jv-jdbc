package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        Manufacturer createManufacturer = new Manufacturer();
        createManufacturer.setName("nameManufacturer");
        createManufacturer.setCountry("countryManufacturer");
        Manufacturer createManufacturer2 = new Manufacturer();
        createManufacturer2.setName("nameManufacturer2");
        createManufacturer2.setCountry("countryManufacturer2");

        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
        manufacturerDao.create(createManufacturer);
        manufacturerDao.create(createManufacturer2);

        manufacturerDao.delete(2L);
        manufacturerDao.delete(1L);

        Manufacturer updateManufacturer = new Manufacturer(2L, "newName", "newCountry", false);
        manufacturerDao.update(updateManufacturer);

        System.out.println(manufacturerDao.get(2L));
    }
}
