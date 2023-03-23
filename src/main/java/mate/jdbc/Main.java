package mate.jdbc;

import java.util.ArrayList;
import java.util.List;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final String PACKAGE = "mate.jdbc";
    public static void main(String[] args) {
        Injector injector = Injector.getInstance(PACKAGE);
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        for (Manufacturer manufacturer : createManufacturers()) {
            manufacturerDao.create(manufacturer);
        }
        List<Manufacturer> all = manufacturerDao.getAll();
        Manufacturer manufacturerForUpdating = new Manufacturer();
        manufacturerForUpdating.setName("Bentley");
        manufacturerForUpdating.setCountry("United Kingdom");
        manufacturerForUpdating.setId(2L);
        manufacturerDao.update(manufacturerForUpdating);
        boolean delete = manufacturerDao.delete(1L);
    }

    private static List<Manufacturer> createManufacturers() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        Manufacturer audi = new Manufacturer();
        audi.setName("Audi");
        audi.setCountry("Germany");
        manufacturers.add(audi);
        Manufacturer bmw = new Manufacturer();
        bmw.setName("BMW");
        bmw.setCountry("Germany");
        manufacturers.add(bmw);
        Manufacturer chevy = new Manufacturer();
        chevy.setName("Chevrolet");
        chevy.setCountry("USA");
        manufacturers.add(chevy);
        return manufacturers;
    }
}
