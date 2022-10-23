package mate.jdbc;

import java.util.ArrayList;
import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("CarbonCar");
        manufacturer.setCountry("Ukraine");

        Manufacturer manufacturerFromDB = manufacturerDao.create(manufacturer);
        System.out.println(manufacturerFromDB);

        List<Manufacturer> manufacturerList = new ArrayList<>()
        for (int i = 0; i < 10; i++) {
            Manufacturer createManufacturer = new Manufacturer();
            createManufacturer.setName("Dynamic Grid " + i);
            createManufacturer.setCountry("USA " + i);
            manufacturerList.add(createManufacturer);
        }

        List<Manufacturer> allManufacturers = manufacturerDao.createAll(manufacturerList);
        System.out.println(allManufacturers);

        List<Manufacturer> getAllManufacturers = manufacturerDao.getAll();
        System.out.println(getAllManufacturers);

        manufacturerFromDB.setCountry("China");
        Manufacturer updateManufacturer = manufacturerDao.update(manufacturerFromDB);
        System.out.println(updateManufacturer);

        boolean deleteManufacturer = manufacturerDao.delete(manufacturerFromDB.getId());
        System.out.println(deleteManufacturer);
    }
}
