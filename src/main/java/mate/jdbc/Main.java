package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer ford = new Manufacturer();
        Manufacturer bmw = new Manufacturer();
        bmw.setName("BMW");
        bmw.setCountry("Germany");
        ford.setName("Ford");
        ford.setCountry("USA");
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        manufacturerDao.create(bmw);
        manufacturerDao.create(ford);

        ford.setCountry("USA");
        manufacturerDao.update(ford);
        System.out.println(manufacturerDao.get(16L).toString());
        for (Manufacturer manufacturer : manufacturerDao.getAll()) {
            System.out.println(manufacturer.toString());
        }
        manufacturerDao.delete(16L);

    }
}
