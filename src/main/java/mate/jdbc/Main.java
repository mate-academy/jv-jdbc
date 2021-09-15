package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.lib.Injector;
import mate.jdbc.util.ConnectionUtil;

import java.sql.Connection;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    public static void main(String[] args) {
        Connection connection = ConnectionUtil.getConnection();
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        System.out.println(manufacturerDao.getAll());
    }
}
