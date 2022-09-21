package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer renault = new Manufacturer();
        renault.setName("renault");
        renault.setCountry("France");
        Manufacturer ford = new Manufacturer();
        ford.setName("ford");
        ford.setCountry("Poland");
        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);
        manufacturerDao.create(renault);
        manufacturerDao.create(ford);
        manufacturerDao.delete(1L);
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        for (Manufacturer manufacturer : manufacturers) {
            System.out.println(manufacturer);
        }
        Manufacturer updateManufacturer = new Manufacturer();
        updateManufacturer.setId(2L);
        updateManufacturer.setCountry("Italy");
        updateManufacturer.setName("dodge");
        manufacturerDao.update(updateManufacturer);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
