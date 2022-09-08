package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer currentManufacturer = new Manufacturer();
        currentManufacturer.setName("BMW Group");
        currentManufacturer.setCountry("Germany");

        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);

        System.out.println("Create");
        Manufacturer savedManufacturer = manufacturerDao.create(currentManufacturer);
        System.out.println(savedManufacturer);

        System.out.println("Get");
        Optional<Manufacturer> requiredManufacturer = manufacturerDao
                .get(savedManufacturer.getId());
        System.out.println(requiredManufacturer.get());

        System.out.println("Get all");
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        for (Manufacturer manufacturer : manufacturers) {
            System.out.println(manufacturer);
        }

        System.out.println("Update");
        currentManufacturer.setCountry("USA");
        currentManufacturer = manufacturerDao.update(currentManufacturer);
        System.out.println(currentManufacturer);

        System.out.println("Delete");
        manufacturerDao.delete(currentManufacturer.getId());
        manufacturers = manufacturerDao.getAll();
        for (Manufacturer manufacturer : manufacturers) {
            System.out.println(manufacturer);
        }
    }
}
