package mate.jdbc;

import java.util.ArrayList;
import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        List<Manufacturer> manufacturerList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setName("Bob" + i);
            manufacturer.setCountry("Bob" + i + "_country");
            manufacturer = manufacturerDao.create(manufacturer);
            manufacturerList.add(manufacturer);
        }
        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        System.out.println(allManufacturers);
        int counter = 1;
        for (Manufacturer manufacturer : allManufacturers) {
            manufacturer.setName("Sue" + counter);
            manufacturer.setCountry("Sue" + counter + "country");
            manufacturerDao.update(manufacturer);
            counter++;
        }
        List<Manufacturer> updatedAllManufacturers = manufacturerDao.getAll();
        System.out.println(updatedAllManufacturers);
        manufacturerDao.delete(updatedAllManufacturers.get(2).getId());
        manufacturerDao.delete(updatedAllManufacturers.get(3).getId());
        manufacturerDao.delete(updatedAllManufacturers.get(4).getId());
        System.out.println(manufacturerDao.getAll());
        System.out.println(manufacturerDao.get(updatedAllManufacturers.get(1).getId()));
    }
}
