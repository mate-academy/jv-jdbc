package mate.jdbc;


import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;
import java.sql.Connection;

public class Main {
    private static final Injector injector = Injector.getInstance("YOUR_PACKAGE");

    public static void main(String[] args) {
        // Connection connection = ConnectionUtil.getConnection();
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        // initialize field values using setters or constructor
        manufacturerDao.create(manufacturer);
        // test other methods from ManufacturerDao
    }
}
