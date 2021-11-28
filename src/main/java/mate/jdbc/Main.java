package mate.jdbc;

import java.lang.reflect.Field;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.SQLDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.User;
import mate.jdbc.models.db.models.Manufacturer;
import mate.jdbc.services.connection.Connector;
import mate.jdbc.services.connection.ConnectorImpl;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        Connector connector = new ConnectorImpl(new User("testUser", "12345678"));
        SQLDao manufacturerDao = (SQLDao) injector.getInstance(SQLDao.class);
        try {
            for (Field field : ManufacturerDao.class.getDeclaredFields()) {
                if (field.getName().equals("connector")) {
                    field.setAccessible(true);
                    field.set(manufacturerDao, connector);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Can't initialize field.");
        }
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("W");
        manufacturer.setCountry("Ukraine");
        manufacturerDao.create(manufacturer);
        System.out.println(manufacturerDao.get(manufacturer.getId()));
        System.out.println(manufacturerDao.getAll());
        manufacturer.setName("mercedes");
        System.out.println(manufacturerDao.update(manufacturer));
        manufacturerDao.delete(manufacturer.getId());
    }
}
