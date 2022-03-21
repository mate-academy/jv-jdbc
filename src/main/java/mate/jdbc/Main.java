package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer bmwManufacturer = manufacturerDao.creat(new Manufacturer(null, "BMW", "Germany"));
        System.out.println(bmwManufacturer);
        Manufacturer mercedesManufacturer = manufacturerDao.creat(new Manufacturer(null, "Mercedes", "Germany"));
        System.out.println(mercedesManufacturer);
        Manufacturer fordManufacturer = manufacturerDao.creat(new Manufacturer(null, "FORD", "USA"));
        System.out.println(fordManufacturer);
        manufacturerDao.deleted(mercedesManufacturer.getId());
        manufacturerDao.update(new Manufacturer(mercedesManufacturer.getId(), "toyota", "Japan"));
        System.out.println(manufacturerDao.get(mercedesManufacturer.getId()));
        List<Manufacturer> allManufacturer = manufacturerDao.getAll();
    }
}
