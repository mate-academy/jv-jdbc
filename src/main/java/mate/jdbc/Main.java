package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturerLexus = new Manufacturer("Lexus", "Japan");
        Manufacturer manufacturerPorsche = new Manufacturer("Porsche", "Germany");
        manufacturerDao.create(manufacturerLexus);
        manufacturerDao.create(manufacturerPorsche);
        manufacturerDao.delete(manufacturerPorsche.getId());
        manufacturerDao.update(manufacturerLexus);
        manufacturerDao.get(manufacturerLexus.getId());
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
