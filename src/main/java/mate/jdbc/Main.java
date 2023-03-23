package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        List<Manufacturer> manufacturers = List.of(
                new Manufacturer("Porsche","Germany"),
                new Manufacturer("Volkswagen","Germany"),
                new Manufacturer("Renault", "France"),
                new Manufacturer("Lamborghini", "Italy")
        );

        for (Manufacturer manufacturer : manufacturers) {
            System.out.println("Before: " + manufacturer);
            manufacturer = manufacturerDao.create(manufacturer);
            System.out.println("After: " + manufacturer);
        }

        for (Manufacturer manufacturer: manufacturerDao.getAll()) {
            System.out.println("get all test" + manufacturer);
        }

        Long id = 1L;
        System.out.println("test get by id" + manufacturerDao.get(id));
        Manufacturer manufacturerUpdate = new Manufacturer(1L, "Mercedes-Benz", "Germany");
        System.out.println("update before:" + manufacturerUpdate);
        manufacturerUpdate = manufacturerDao.update(manufacturerUpdate);
        System.out.println("update after:" + manufacturerUpdate);
        Long idDelete = 2L;
        manufacturerDao.delete(idDelete);

        for (Manufacturer manufacturer : manufacturerDao.getAll()) {
            System.out.println(manufacturer);
        }
    }
}
