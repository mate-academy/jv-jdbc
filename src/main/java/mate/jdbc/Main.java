package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.models.Manufacturer;

public class Main {
    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
        System.out.println((manufacturerDao.get((long)2)).get());
        manufacturerDao.delete((long)5);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId((long)6);
        manufacturer.setName("Bohdan");
        manufacturer.setCountry("India");
        manufacturerDao.create(manufacturer);
        manufacturer.setId((long)8);
        manufacturer.setName("BMW");
        manufacturer.setCountry("Germany");
        manufacturerDao.update(manufacturer);
        List<Manufacturer> list = manufacturerDao.getAll();
        for (Manufacturer parameter:list) {
            System.out.println(parameter);
        }
    }
}
