package mate.jdbc;

import java.lang.reflect.Field;
import mate.jdbc.dao.manufacturer.ManufacturerDao;
import mate.jdbc.dao.manufacturer.ManufacturerDaoImpl;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.User;
import mate.jdbc.models.db.models.Manufacturer;
import mate.jdbc.services.connection.Connector;
import mate.jdbc.services.connection.ConnectorImpl;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        Connector connector = new ConnectorImpl(new User("testUser", "12345678"));
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        initializeConnectorInDao(manufacturerDao, connector);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("volkswagen");
        manufacturer.setCountry("Ukraine");
        manufacturerDao.create(manufacturer);
        System.out.println(manufacturerDao.get(manufacturer.getId()));
        System.out.println(manufacturerDao.getAll());
        manufacturer.setName("Germany");
        System.out.println(manufacturerDao.update(manufacturer));
        manufacturerDao.delete(manufacturer.getId());
    }

    public static void initializeConnectorInDao(ManufacturerDao dao, Connector connector) {
        try {
            for (Field field : ManufacturerDaoImpl.class.getDeclaredFields()) {
                if (field.getName().equals("connector")) {
                    field.setAccessible(true);
                    field.set(dao, connector);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Can't initialize field.");
        }
    }
}
