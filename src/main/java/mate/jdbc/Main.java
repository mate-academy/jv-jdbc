package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        System.out.println("Test create");
        Manufacturer ford = new Manufacturer("FORD", "USA");
        ford = manufacturerDao.create(ford);
        Manufacturer audi = new Manufacturer("AUDI", "Germany");
        audi = manufacturerDao.create(audi);
        Manufacturer lanos = new Manufacturer("LANOS", "UKRAINE");
        lanos = manufacturerDao.create(lanos);
        System.out.println("Test getAll");
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("Test update. Changed name in lanos variable to tesla");
        lanos.setName("tesla");
        manufacturerDao.update(lanos);
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("Test get");
        System.out.println(manufacturerDao.get(lanos.getId()));
        System.out.println("Test delete");
        manufacturerDao.delete(audi.getId());
        System.out.println("================================");
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(ford.getId());

    }
}
