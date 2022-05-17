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

        Manufacturer manufacturer = new Manufacturer("Den","Ukraine");
        Manufacturer manufacturerCreate = manufacturerDao.create(manufacturer);
        Manufacturer manufacturerToUpdate = manufacturerDao.create(manufacturerCreate);
        Manufacturer manufacturerToDelete = manufacturerDao.create(manufacturerCreate);
        Manufacturer manufacturerUpdate
                = new Manufacturer(manufacturerToUpdate.getId(),"Denys","Ukraine");
        manufacturerDao.update(manufacturerToUpdate);
        System.out.println("manufacturerDao.get() = "
                + manufacturerDao.get(manufacturerCreate.getId()));
        manufacturerDao.delete(manufacturerToDelete.getId());
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
