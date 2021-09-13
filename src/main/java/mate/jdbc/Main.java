package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
        System.out.println("Manufacturer List");
        System.out.println(manufacturerDao.getAll());
        System.out.println();

        System.out.println(manufacturerDao.get(2L));
        System.out.println(manufacturerDao.get(55L));

//        Manufacturer noname = new Manufacturer("Noname", "Noland");
////        manufacturerDao.create(noname);
////        Manufacturer veryBadCompany = new Manufacturer("Very Bad Company", "Russia");
////        manufacturerDao.create(veryBadCompany);
//
////        Manufacturer some = new Manufacturer("Some", "Someland");
////        some.setId(3L);
////        System.out.println(manufacturerDao.update(some));
//
        System.out.println(manufacturerDao.delete(3L));
        System.out.println(manufacturerDao.getAll());

    }
}
