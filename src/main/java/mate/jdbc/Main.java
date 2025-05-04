package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer vw = new Manufacturer("VW", "Germany");
        manufacturerDao.create(vw);
        manufacturerDao.create(new Manufacturer("Dodge", "USA"));
        manufacturerDao.create(new Manufacturer("Hyundai", "South Korea"));
        manufacturerDao.getAll().forEach(System.out::println);
        vw.setName("VW Golf");
        manufacturerDao.update(vw);
        System.out.println(manufacturerDao.get(vw.getId()));
        manufacturerDao.delete(vw.getId());
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
