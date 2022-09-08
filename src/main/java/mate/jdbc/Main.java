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
        Manufacturer audi = new Manufacturer(null, "Audi", "Germany");
        Manufacturer tesla = new Manufacturer(null, "Tesla", "USA");
        mercedes = manufacturerDao.create(mercedes);
        audi = manufacturerDao.create(audi);
        tesla = manufacturerDao.create(tesla);
        System.out.println("Create 3 manufacturers");
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("***");
        tesla.setName("Toyota");
        manufacturerDao.update(tesla);
        System.out.println("Change manufacturer name Tesla to Audi");
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("***");
        manufacturerDao.delete(mercedes.getId());
        System.out.println("Delete manufacturer mercedes from database");
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("***");
        Optional<Manufacturer> getManufacturer = manufacturerDao.get(audi.getId());
        System.out.println("Get manufacturer Audi from database");
        System.out.println(getManufacturer.get());
    }
}
