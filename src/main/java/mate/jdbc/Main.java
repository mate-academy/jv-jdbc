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
        System.out.println(manufacturerDao.get(manufacturerLexus.getId()));
        manufacturerDao.delete(manufacturerPorsche.getId());
        manufacturerLexus.setCountry("USA");
        manufacturerDao.update(manufacturerLexus);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
