package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static ManufacturerDao manufacturerDao;

    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        testCreate();
        testUpdate();
        testDelete();
    }

    private static void testCreate() {
        System.out.printf("%nTesting manufacturer creation...%n");

        Manufacturer manufacturer1 = new Manufacturer("AUDI", "Germany");
        Manufacturer manufacturer2 = new Manufacturer("BMW", "Germany");
        Manufacturer manufacturer3 = new Manufacturer("Nissan", "Japan");
        manufacturerDao.create(manufacturer1);
        manufacturerDao.create(manufacturer2);
        manufacturerDao.create(manufacturer3);

        manufacturerDao.getAll().forEach(System.out::println);
    }

    private static void testUpdate() {
        System.out.printf("%nTesting manufacturer updating...%n");

        long id = 2L;
        Optional<Manufacturer> manufacturerToUpdate = manufacturerDao.get(id);
        if (manufacturerToUpdate.isEmpty()) {
            System.out.printf("Manufacturer with id=%d not found%n", id);
            return;
        }
        Manufacturer toUpdate = manufacturerToUpdate.get();
        toUpdate.setName("Mercedes Benz");
        manufacturerDao.update(toUpdate);

        manufacturerDao.get(id).ifPresent(System.out::println);
    }

    private static void testDelete() {
        System.out.printf("%nTesting manufacturer deletion...%n");

        long id = 2L;
        boolean isDeleted = manufacturerDao.delete(id);
        if (isDeleted) {
            System.out.printf("Manufacturer with id=%d deleted successfully%n", id);
        } else {
            System.out.println("Failed to delete manufacturer with id=" + id);
        }

        manufacturerDao.getAll().forEach(System.out::println);
    }
}
