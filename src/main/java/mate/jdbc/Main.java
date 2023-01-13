package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {

        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        System.out.println(manufacturerDao.getAll());
        Manufacturer fiat = new Manufacturer();
        fiat.setName("Fiat");
        fiat.setCountry("Germany");
        System.out.println(manufacturerDao.create(fiat));
        System.out.println(manufacturerDao.getAll());
        fiat.setId(fiat.getId());
        fiat.setCountry("Italy");
        System.out.println(manufacturerDao.update(fiat));
        System.out.println(manufacturerDao.getAll());
        System.out.println(manufacturerDao.delete(8L));
        System.out.println(manufacturerDao.getAll());
        manufacturerDao.get(3L);
    }
}
