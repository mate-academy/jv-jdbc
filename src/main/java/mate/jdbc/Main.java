package mate.jdbc;

import java.io.IOException;
import java.util.Properties;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.impl.ManufacturerDaoImpl;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.service.CreateDataBase;
import mate.jdbc.service.FileReaderService;
import mate.jdbc.service.impl.CreateDataBaseImpl;
import mate.jdbc.service.impl.FileReaderServiceImpl;
import mate.jdbc.util.DataSource;
import mate.jdbc.util.FileUtils;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static final String PROPERTIES = "/db.properties";
    private static final String INIT_DB = "/init_db.sql";
    private static DataSource dataSource;

    public static void main(String[] args) {
        try {
            Properties properties = FileUtils.readProperties(PROPERTIES);
            dataSource = new DataSource(properties);
            CreateDataBase createDB = new CreateDataBaseImpl(dataSource);
            createDB.createdTable(FileUtils.readFile(INIT_DB));
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException("");
        }

//        ManufacturerDao manufacturerDao
//                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
//        Manufacturer manufacturer = new Manufacturer();
//        // initialize field values using setters or constructor
//        manufacturer.setName("BMW");
//        manufacturer.setCountry("Germany");
//        manufacturerDao.create(manufacturer);
        // test other methods from ManufacturerDao

        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl(dataSource);
        Manufacturer manufacturerCreate = new Manufacturer("Den","Ukraine");
        Manufacturer manufacturerUpdate = new Manufacturer(1L,"Denys","Ukraine");
        manufacturerDao.create(manufacturerCreate);
        manufacturerDao.update(manufacturerUpdate);
        System.out.println("manufacturerDao.get(1L) = " + manufacturerDao.get(1L));
        manufacturerDao.delete(3L);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
