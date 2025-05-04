package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturerKia =
                new Manufacturer("KIA", "Japan");
        Manufacturer manufacturerAlfaRomeo =
                new Manufacturer("Alfa Romeo", "Italy");
        manufacturerDao.create(manufacturerKia);
        manufacturerDao.create(manufacturerAlfaRomeo);
        System.out.println("Manufacturer KIA was created: "
                + manufacturerDao.get(manufacturerKia.getId()));
        System.out.println("Manufacturer Alfa Romeo was created: "
                + manufacturerDao.get(manufacturerAlfaRomeo.getId()));
        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        System.out.println("All manufacturers from DB: " + allManufacturers);
        System.out.println("Size of current manufacturers list: "
                + allManufacturers.size());
        manufacturerDao.delete(manufacturerAlfaRomeo.getId());
        System.out.println("Size of current manufacturers list: "
                + manufacturerDao.getAll().size());
        manufacturerKia.setCountry("South Korea");
        Manufacturer updatedManufacturerKia = manufacturerDao.update(manufacturerKia);
        System.out.println("Manufacturer KIA was updated: " + updatedManufacturerKia);
        System.out.println("All manufacturers from DB: " + manufacturerDao.getAll());
    }
}
