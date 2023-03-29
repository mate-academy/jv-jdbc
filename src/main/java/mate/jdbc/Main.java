package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static Manufacturer bmwManufacturer = new Manufacturer("BMW","Germany");
    private static Manufacturer volvoManufacturer = new Manufacturer("Volvo","Sweden");
    private static Manufacturer fordManufacturer = new Manufacturer("Ford","USA");
    private static Injector injector = Injector.getInstance("mate.jdbc");
    private static ManufacturerDao manufacturerDao
            = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

    public static void main(String[] args) {
        List<Manufacturer> manufacturers = List.of(
                bmwManufacturer, volvoManufacturer, fordManufacturer
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
