package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) INJECTOR.getInstance(ManufacturerDao.class);
        Manufacturer manufacturerOpel = new Manufacturer("Opel", "Germany");
        Manufacturer manufacturerFord = new Manufacturer("Ford", "USA");
        Manufacturer manufacturerKia = new Manufacturer("KIA", "China");

        manufacturerDao.create(manufacturerOpel);
        manufacturerDao.create(manufacturerFord);
        manufacturerDao.create(manufacturerKia);

        Long id = 1L;
        System.out.println(manufacturerDao.get(id));
        System.out.println("first check");
        manufacturerDao.getAll().forEach(System.out::println);

        manufacturerFord.setCountry("Ukraine");
        manufacturerDao.update(manufacturerFord);
        manufacturerDao.delete(id);
        System.out.println("second check");
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
