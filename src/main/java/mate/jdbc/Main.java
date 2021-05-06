package mate.jdbc;

import java.sql.Connection;
import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

public class Main {
    public static void main(String[] args) {
        Connection connection = ConnectionUtil.getConnection();
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();

        List<Manufacturer> all = manufacturerDao.getAll();
        System.out.println(all);

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(3L);
        manufacturer.setCountry("China");
        manufacturer.setName("Cherry");
        System.out.println(manufacturerDao.update(manufacturer));

        List<Manufacturer> all1 = manufacturerDao.getAll();
        System.out.println(all1);
    }
}
