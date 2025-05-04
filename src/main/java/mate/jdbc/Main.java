package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturerAudi = new Manufacturer();
        manufacturerAudi.setName("Audi");
        manufacturerAudi.setCountry("Germany");
        manufacturerDao.create(manufacturerAudi);
        Manufacturer getFromDb = manufacturerDao.get(7L).get();
        System.out.println(getFromDb);
        System.out.println(manufacturerDao.getAll());
        getFromDb.setName("Tavrija");
        manufacturerDao.update(getFromDb);
        manufacturerDao.delete(1L);
        System.out.println(manufacturerDao.getAll());
    }
}

