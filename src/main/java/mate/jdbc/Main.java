package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {

    public static void main(String[] args) {
        Manufacturer manufacturerMazda = new Manufacturer();
        manufacturerMazda.setName("mazda");
        manufacturerMazda.setCountry("japan");
        Manufacturer manufacturerSkoda = new Manufacturer();
        manufacturerSkoda.setName("skoda");
        manufacturerSkoda.setCountry("czech republic");
        Manufacturer manufacturerMercedes = new Manufacturer();
        manufacturerMercedes.setId(2L);
        manufacturerMercedes.setName("mercedes");
        manufacturerMercedes.setCountry("germany");
        Injector injector = Injector.getInstance("mate");
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        manufacturerDao.create(manufacturerMazda);
        manufacturerDao.create(manufacturerSkoda);
        for (Manufacturer temp : manufacturerDao.getAll()) {
            System.out.println(temp);
        }
        manufacturerDao.update(manufacturerMercedes);
        System.out.println(manufacturerDao.get(2L));
        System.out.println(manufacturerDao.delete(1L));
    }
}
