package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        List<Manufacturer> manufacturers;
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Volvo");
        manufacturer.setCountry("Sweden");
        //manufacturerDao.delete(2L);
        //manufacturerDao.create(manufacturer);
        manufacturers = manufacturerDao.getAll();
        manufacturers.forEach(System.out::println);
    }
}
