package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer mercedes = new Manufacturer(null, "Mercedes", "Germany");
        Manufacturer lada = new Manufacturer(null, "Lada", "Ukraine");
        Manufacturer mazda = new Manufacturer(null, "Mazda", "Japan");
        mercedes = manufacturerDao.create(mercedes);
        lada = manufacturerDao.create(lada);
        mazda = manufacturerDao.create(mazda);
        System.out.println("Create 3 manufacturers");
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("---");
        mazda.setName("Toyota");
        manufacturerDao.update(mazda);
        System.out.println("Change manufacturer name Mazda to Toyota");
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("---");
        manufacturerDao.delete(mercedes.getId());
        System.out.println("Delete manufacturer mercedes from database");
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("---");
        Optional<Manufacturer> getManufacturer = manufacturerDao.get(lada.getId());
        System.out.println("Get manufacturer Lada from database");
        System.out.println(getManufacturer);
    }
}
