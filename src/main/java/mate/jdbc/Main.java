package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("ZAZ");
        manufacturer.setCountry("Ukraine");
        System.out.println(manufacturer);
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        manufacturer = manufacturerDao.create(manufacturer);
        System.out.println(manufacturerDao.get(manufacturer.getId()));
        Manufacturer manufacturerToUpdate = new Manufacturer();
        manufacturerToUpdate.setId(manufacturer.getId());
        manufacturerToUpdate.setName("BMW");
        manufacturerToUpdate.setCountry("Germany");
        manufacturer = manufacturerDao.update(manufacturerToUpdate);
        System.out.println(manufacturer);
        manufacturerDao.delete(manufacturer.getId());
        List<Manufacturer> allManufacturersFromDb = manufacturerDao.getAll();
        System.out.println(allManufacturersFromDb);
    }
}
