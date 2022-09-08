package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
        System.out.println(manufacturerDao.get(2L));
        System.out.println(manufacturerDao.getAll());
        Manufacturer test = new Manufacturer(1L, "ferrari", "italy");
        manufacturerDao.update(test);
        System.out.println(manufacturerDao.getAll());
        System.out.println(manufacturerDao.delete(2L));
        System.out.println(manufacturerDao.getAll());
        System.out.println(manufacturerDao.getAll());
    }
}
