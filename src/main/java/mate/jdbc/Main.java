package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {

    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        List<Manufacturer> manufacturers = List.of(
                new Manufacturer("BMW", "Germany"),
                new Manufacturer("Volvo", "Sweden"),
                new Manufacturer("Ford", "USA")

        );

        System.out.println("Test create:");
        for (Manufacturer manufacturer : manufacturers) {
            System.out.println("Before: " + manufacturer);
            manufacturer = manufacturerDao.create(manufacturer);
            System.out.println("After:  " + manufacturer);
        }

        Long id = 34L;
        System.out.println("\nTest get manufacturer by id = " + id);
        System.out.println("return = " + manufacturerDao.get(id));

        System.out.println("\nTest delete manufacturer by id = " + id);
        System.out.println("return = " + manufacturerDao.delete(id));
        System.out.println("after get(" + id + ") = " + manufacturerDao.get(id));

        Manufacturer manufacturerUpdate
                = new Manufacturer(35L, "Seat", "Spain");
        System.out.println("\nTest update manufacturer " + manufacturerUpdate);
        System.out.println("return = " + manufacturerDao.update(manufacturerUpdate));

        System.out.println("\nTest get all manufacturers");
        System.out.println("return:");
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
