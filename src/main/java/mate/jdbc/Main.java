package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = 
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Koller");
        manufacturer.setCountry("Argentina");
        manufacturerDao.create(manufacturer);
        System.out.println(manufacturerDao.get(3L).toString());
        manufacturer.setName("Ford");
        manufacturer.setId(3L);
        manufacturer.setCountry("United States");
        System.out.println(manufacturerDao.update(manufacturer));
        System.out.println(manufacturerDao.delete(manufacturer.getId()));
        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        for (Manufacturer m : allManufacturers) {
            System.out.println(m);
        }
    }
}
