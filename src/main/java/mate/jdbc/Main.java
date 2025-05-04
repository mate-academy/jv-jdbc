package mate.jdbc;

import mate.jdbc.lib.Injector;
import mate.jdbc.lib.dao.ManufacturerDao;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer chevrolet = new Manufacturer("Chevrolet", "USA");
        Manufacturer ford = new Manufacturer("Ford", "USA");
        Manufacturer volvo = new Manufacturer("Volvo", "Sweden");
        Manufacturer honda = new Manufacturer("Honda", "Japan");
        manufacturerDao.create(chevrolet);
        manufacturerDao.create(ford);
        manufacturerDao.create(volvo);
        manufacturerDao.create(honda);
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(manufacturerDao.get(2L));
        // -> manufacturerDao.Manufacturer{id=2, name='Ford', country='USA'}
        honda.setId(2L);
        System.out.println(manufacturerDao.update(honda));
        // -> Manufacturer{id=2, name='Honda', country='Japan'}
        System.out.println(manufacturerDao.delete(2L));
        // -> true
        //                  DB
        //        Manufacturer{id=1, name='Chevrolet', country='USA'}
        //        Manufacturer{id=3, name='Volvo', country='Sweden'}
    }
}
