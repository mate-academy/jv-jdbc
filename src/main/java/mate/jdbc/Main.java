package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer firstManufacturer = new Manufacturer("Mercedes-Benz", "German");
        Manufacturer secondManufacturer = new Manufacturer("Peugeot", "France");
        manufacturerDao.create(firstManufacturer);
        manufacturerDao.delete(3L);
        manufacturerDao.create(secondManufacturer);
        secondManufacturer.setName("Toyota");
        secondManufacturer.setCountry("Japan");
        manufacturerDao.update(secondManufacturer);
        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        System.out.println(allManufacturers);
        System.out.println(manufacturerDao.get(secondManufacturer.getId()));
    }
}
