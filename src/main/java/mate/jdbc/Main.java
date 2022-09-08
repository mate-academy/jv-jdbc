package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;

public class Main {
    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();

        //Manufacturer test = new Manufacturer(1L, "ferrari", "italy");
        System.out.println(manufacturerDao.delete(4L));
        System.out.println(manufacturerDao.getAll());

    }
}
