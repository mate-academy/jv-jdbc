package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        Manufacturer createManufacturerHonda = new Manufacturer();
        createManufacturerHonda.setName("Honda");
        createManufacturerHonda.setCountry("Japan");
        Manufacturer createManufacturerToyota = new Manufacturer();
        createManufacturerToyota.setName("Toyota");
        createManufacturerToyota.setCountry("Japan");

        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
        manufacturerDao.create(createManufacturerHonda);
        manufacturerDao.create(createManufacturerToyota);

        manufacturerDao.delete(createManufacturerToyota.getId());

        Manufacturer updateManufacturerToyota = new Manufacturer(createManufacturerToyota.getId(),
                "newHonda", "newJapan");
        manufacturerDao.update(updateManufacturerToyota);

        System.out.println(manufacturerDao.get(createManufacturerToyota.getId()));
    }
}
