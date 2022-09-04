package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer manufacturer = new Manufacturer();
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        manufacturer.setName("Mazda");
        manufacturer.setCountry("Japan");
        manufacturerDao.create(manufacturer);
        System.out.println(manufacturer);
        System.out.println("--------------------");

        System.out.println("GetAll manufacturer");
        List<Manufacturer> allManufacturer = manufacturerDao.getAll();
        for (Manufacturer item : allManufacturer) {
            System.out.println(item);
        }
        System.out.println("--------------------");

        System.out.println("Get manufacturer");
        Optional<Manufacturer> optional = manufacturerDao.get(28L);
        System.out.println(optional);
        System.out.println("--------------------");

        System.out.println("Upfate manufacturer");
        Manufacturer updateManufacturer = new Manufacturer();
        updateManufacturer.setId(15L);
        updateManufacturer.setName("Kia");
        updateManufacturer.setCountry("Korea");
        System.out.println(manufacturerDao.update(updateManufacturer));
        System.out.println("--------------------");

        System.out.println("Delete manufacturer");
        System.out.println(manufacturerDao.delete(19L));
        System.out.println("--------------------");
    }
}
