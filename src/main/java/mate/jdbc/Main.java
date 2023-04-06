package mate.jdbc;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.manufacturer.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    public static final ManufacturerDao manufacturerDao
            = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

    public static void main(String[] args) {
        Connection connection = ConnectionUtil.getConnection();
        System.out.println(connection);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setCountry("Ukraine");
        manufacturer.setName("Artem");
        manufacturer.setId(2L);

        System.out.println(manufacturerDao.delete(3L));

        Optional<Manufacturer> manufacturerThree = manufacturerDao.get(1L);
        if (manufacturerThree.isPresent()) {
            System.out.println(manufacturerThree.get());
        } else {
            System.out.println("Error");
        }

        List<Manufacturer> manufacturerOne = manufacturerDao.getAll();
        System.out.println(manufacturerOne);

        Manufacturer update = manufacturerDao.update(manufacturer);
        System.out.println(update);

        Manufacturer manufacturerTwo = manufacturerDao.create(manufacturer);
        System.out.println(manufacturerTwo);
    }
}
