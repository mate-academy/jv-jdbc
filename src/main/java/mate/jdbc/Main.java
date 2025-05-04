package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.models.Manufacturer;

public class Main {
    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("Insert new record in DB");
        Manufacturer manufacturerZaz = new Manufacturer("ZAZ", "Ukraine");
        Manufacturer savedManufacturerZaz = manufacturerDao.create(manufacturerZaz);
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("Update record in DB");
        Manufacturer manufacturerZazDaewoo =
                new Manufacturer(savedManufacturerZaz.getId(), "ZAZ Daewoo", "Ukraine");
        manufacturerDao.update(manufacturerZazDaewoo);
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("Delete record from DB");
        manufacturerDao.delete(savedManufacturerZaz.getId());
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
