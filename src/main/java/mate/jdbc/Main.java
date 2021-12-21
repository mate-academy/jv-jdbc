package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.impl.ManufacturerDaoImpl;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Mazda");
        manufacturer.setCountry("Japan");
        System.out.println("All records in the DB: " + manufacturerDao.getAll());
        manufacturer = manufacturerDao.create(manufacturer);
        System.out.println("Saved to DB: " + manufacturer);
        System.out.println("All records in the DB after creating: " + manufacturerDao.getAll());
        System.out.println("Record by index 1L: " + manufacturerDao.get(1L));
        manufacturer.setName("Subaru");
        System.out.println("Updated record: " + manufacturerDao.update(manufacturer));
        System.out.println("All records in the DB after updating: " + manufacturerDao.getAll());
        boolean is_deleted = manufacturerDao.delete(manufacturer.getId());
        System.out.println("Delete: " + manufacturer);
        System.out.println("All records in the DB after deleting: " + manufacturerDao.getAll());

    }
}
