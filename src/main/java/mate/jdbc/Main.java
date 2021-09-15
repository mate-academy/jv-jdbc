package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

import java.sql.Connection;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    public static void main(String[] args) {
        Connection connection = ConnectionUtil.getConnection();
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        System.out.println(manufacturerDao.getAll());
        Manufacturer manufacturer = new Manufacturer("DDD", "Ffff");
        manufacturerDao.create(manufacturer);
        manufacturerDao.delete(manufacturer.getId());
        System.out.println(manufacturerDao.get(5L));
        manufacturer.setCountry("UKRAINA");
        manufacturerDao.update(manufacturer);

    }
}
