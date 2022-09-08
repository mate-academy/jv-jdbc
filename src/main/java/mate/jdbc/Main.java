package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                        .getInstance(ManufacturerDao.class);
        Manufacturer manufacturerFerrari = new Manufacturer(null, "Ferrari", "Italy");
        Manufacturer manufacturerBmw = new Manufacturer(null, "BMW", "Germany");
        Manufacturer manufacturerPorsche = new Manufacturer(null, "Porsche", "Germany");
        manufacturerDao.create(manufacturerFerrari);
        manufacturerDao.create(manufacturerBmw);
        manufacturerDao.create(manufacturerPorsche);
        System.out.println("Three manufacturers was added");
        manufacturerDao.getAll().forEach(System.out::println);
        manufacturerFerrari.setCountry("Ukraine");
        manufacturerDao.update(manufacturerFerrari);
        System.out.println("Ferrari manufacturer was updated");
        manufacturerDao.getAll().forEach(System.out::println);
        manufacturerDao.delete(manufacturerBmw.getId());
        System.out.println("BMW manufacturer was deleted");
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(manufacturerDao.get(manufacturerFerrari.getId()));
        System.out.println("Ferrari manufacturer was gotten");
    }
}
