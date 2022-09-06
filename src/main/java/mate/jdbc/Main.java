package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

import java.util.List;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Suzuki");
        manufacturer.setCountry("Japan");
        manufacturerDao.create(manufacturer);
        System.out.println(manufacturer);

        System.out.println("******************* GET ALL ******************");
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        manufacturers.forEach(System.out::println);
        System.out.println("******************* UPDATE ******************");
        //manufacturer.setName(new String());
        //manufacturerDao.update(manufacturer);
        System.out.println(manufacturer);
        manufacturerDao.delete(16L);
    }
}
