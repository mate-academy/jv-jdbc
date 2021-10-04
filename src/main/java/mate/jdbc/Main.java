package mate.jdbc;

import java.sql.Connection;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

public class Main {
    public static void main(String[] args) {
        Connection connection = ConnectionUtil.getConnection();
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        manufacturerDao.create(new Manufacturer(4L, "audi", "Germany"));
        System.out.println(manufacturerDao.get(5L).get());
        manufacturerDao.update(new Manufacturer(4L, "volkswagen", "Germany"));
        manufacturerDao.delete(4L);
        System.out.println(manufacturerDao.getAll());
    }
}
