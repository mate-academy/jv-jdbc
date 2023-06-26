package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc.dao");
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer manufacturer = new Manufacturer("Name2", "Country2");
        Manufacturer createManufacturer = manufacturerDao.create(manufacturer);
        System.out.println(createManufacturer);

        Optional<Manufacturer> getManufacturerCheck = manufacturerDao.get(8L);
        System.out.println(getManufacturerCheck);

        boolean deleteManufacturerCheck = manufacturerDao.delete(9L);
        System.out.println(deleteManufacturerCheck);

        List<Manufacturer> allManufacturersCheck = manufacturerDao.getAll();
        System.out.println(allManufacturersCheck);

    }
}
