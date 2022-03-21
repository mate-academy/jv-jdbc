package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer bmwManufacturer = manufacturerDao
                .create(new Manufacturer(null, "BMW", "Germany"));
        System.out.println(bmwManufacturer);
        Manufacturer mercedesManufacturer = manufacturerDao
                .create(new Manufacturer(null, "Mercedes", "Germany"));
        System.out.println(mercedesManufacturer);
        Manufacturer fordManufacturer = manufacturerDao
                .create(new Manufacturer(null, "FORD", "USA"));
        System.out.println(fordManufacturer);
        manufacturerDao.deleted(mercedesManufacturer.getId());
        fordManufacturer.setCountry("Japan");
        fordManufacturer.setName("Toyota");
        manufacturerDao.updated(fordManufacturer);
        System.out.println(manufacturerDao.get(fordManufacturer.getId()));
        List<Manufacturer> allManufacturer = manufacturerDao.getAll();
        allManufacturer.forEach(System.out::println);
    }
}
