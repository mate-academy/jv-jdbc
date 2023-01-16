package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class Main {
    private static final Injector injector = Injector.getInstance("src/main/java/mate.jdbc/dao");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        // CREATE
//        Manufacturer manufacturer = new Manufacturer();
//        manufacturer.setId(45L);
//        manufacturer.setName("Ram Trucks");
//        manufacturer.setCountry("United States");
//        manufacturerDao.create(manufacturer);

        // GET ALL

//        List<Manufacturer> manufacturers = manufacturerDao.getAll();
//        for (Manufacturer manufacturer : manufacturers) {
//            System.out.println(manufacturer);
//        }

        // DELETE

//        manufacturerDao.delete(44L);

        // GET
//        Optional<Manufacturer> manufacturer = manufacturerDao.get(12L);
//        System.out.println(manufacturer);

        // UPDATE

//        Manufacturer update = manufacturerDao.update(manufacturer);
//        System.out.println(update);
    }
}

