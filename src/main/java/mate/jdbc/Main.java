package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer toyota = new Manufacturer("Toyota", "Japan");
        Manufacturer seat = new Manufacturer("Seat", "Italy");
        Manufacturer reno = new Manufacturer("Reno", "France");

        Manufacturer mercedes = new Manufacturer(1L,"Mercedes", "Germany");
        manufacturerDao.update(mercedes);
        manufacturerDao.delete(3L);
        manufacturerDao.create(toyota);
        manufacturerDao.create(seat);
        manufacturerDao.create(reno);

        System.out.println(manufacturerDao.get(4L));
        manufacturerDao.getAll().forEach(System.out::println);

    }
}
