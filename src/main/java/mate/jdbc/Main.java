package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer bogdan = manufacturerDao.create(new Manufacturer("Bogdan", "Ukraine"));
        Manufacturer bmw = manufacturerDao.create(new Manufacturer("BMW", "Estonia"));
        Manufacturer mercedes = manufacturerDao.create(new Manufacturer("Mercedes", "Poland"));
        Manufacturer porsche = manufacturerDao.create(new Manufacturer("Porsche", "Italy"));
        Manufacturer wv = manufacturerDao.create(new Manufacturer("Volkswagen", "Spain"));
        System.out.println("Delete porsche manufacturer by id: "
                + manufacturerDao.delete(porsche.getId()));
        System.out.println("Get wv manufacturer by id: "
                + manufacturerDao.get(wv.getId()));
        System.out.println("Update bmw manufacturer : "
                + manufacturerDao.update(new Manufacturer(bmw.getId(),
                "SuperBMW", "Germany")));
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
