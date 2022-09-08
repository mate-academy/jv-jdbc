package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Acura");
        manufacturer.setCountry("Japan");
        Manufacturer manufacturerInDb = manufacturerDao.create(manufacturer);
        System.out.println("was created into db");
        System.out.println(manufacturerInDb);
        System.out.println("----------------");

        Optional<Manufacturer> optional = manufacturerDao.get(5L);
        System.out.println("get by id");
        System.out.println(optional);
        System.out.println("----------------");

        Manufacturer newManufacturer = new Manufacturer();
        newManufacturer.setName("Isuzu");
        newManufacturer.setCountry("Japan");
        manufacturerDao.create(newManufacturer);
        Manufacturer forUpdate = new Manufacturer();
        forUpdate.setName("Infiniti");
        forUpdate.setCountry("Japan");
        forUpdate.setId(4L);
        System.out.println("update in DB");
        System.out.println(manufacturerDao.update(forUpdate));
        System.out.println("deleted from DB");
        System.out.println(manufacturerDao.delete(7L));
        System.out.println("----------------");

        System.out.println("get all from DB");
        System.out.println(manufacturerDao.getAll());
        System.out.println("----------------");
    }
}
