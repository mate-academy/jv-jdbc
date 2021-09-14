package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;

public class Main {
    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
        System.out.println("Manufacturer List");
        System.out.println(manufacturerDao.getAll());
        System.out.println();

        System.out.println(manufacturerDao.get(2L));
        System.out.println(manufacturerDao.get(55L));

        System.out.println(manufacturerDao.delete(3L));
        System.out.println(manufacturerDao.getAll());

    }
}
