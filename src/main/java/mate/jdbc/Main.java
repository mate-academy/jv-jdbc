package mate.jdbc;

import java.util.List;
import mate.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        List<Manufacturer> manufacturers = List.of(
                new Manufacturer("Volkswagen", "Germany"),
                new Manufacturer("Daimler", "UK"),
                new Manufacturer("General Motors", "USA"),
                new Manufacturer("Toyota", "Japan")
        );
        for (Manufacturer manufacturer : manufacturers) {
            manufacturerDao.create(manufacturer);
        }
        System.out.println("manufacturerDao.getAll() = "
                + manufacturerDao.getAll());
        System.out.println("manufacturerDao.get(2L) = "
                + manufacturerDao.get(2L));
        manufacturers.get(0).setId(257L);
        System.out.println(
                "manufacturerDao.update(manufacturers.get(0)) = "
                        + manufacturerDao.update(manufacturers.get(0)));
        System.out.println("manufacturerDao.delete(1L) = "
                + manufacturerDao.delete(1L));
    }
}
