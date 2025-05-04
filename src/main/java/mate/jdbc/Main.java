package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);
        Manufacturer fiat = manufacturerDao
                .create(new Manufacturer("Fiat", "Ukraine"));
        Manufacturer toyota = manufacturerDao
                .create(new Manufacturer("Toyota", "Japan"));
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(manufacturerDao.delete(toyota.getId()));
        fiat.setCountry("Italia");
        Manufacturer audi = manufacturerDao
                .create(new Manufacturer("Audi", "Europa"));
        audi.setCountry("Germany");
        manufacturerDao.update(fiat);
        manufacturerDao.update(audi);
        for (Manufacturer manufacturer : manufacturerDao.getAll()) {
            System.out.println(manufacturer);
        }
    }
}
