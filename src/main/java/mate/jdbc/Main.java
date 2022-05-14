package mate.jdbc;

import java.io.IOException;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.service.CreateDataBase;
import mate.jdbc.service.impl.CreateDataBaseImpl;
import mate.jdbc.util.FileUtils;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static final String INIT_DB = "/init_db.sql";

    public static void main(String[] args) {
        try {
            CreateDataBase createDB = new CreateDataBaseImpl();
            createDB.createdTable(FileUtils.readFile(INIT_DB));
        } catch (IOException e) {
            throw new RuntimeException("Can`t create table for database");
        }

        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer manufacturerCreate = new Manufacturer("Den","Ukraine");
        Manufacturer manufacturerUpdate = new Manufacturer(1L,"Denys","Ukraine");
        for (int i = 0; i < 3; i++) {
            manufacturerDao.create(manufacturerCreate);
        }
        manufacturerDao.update(manufacturerUpdate);
        System.out.println("manufacturerDao.get(1L) = " + manufacturerDao.get(1L));
        manufacturerDao.delete(3L);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
