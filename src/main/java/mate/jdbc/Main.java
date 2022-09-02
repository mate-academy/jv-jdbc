package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        System.out.println("Create manufacturer");
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        // initialize field values using setters or constructor
        manufacturer.setName("Mercedes");
        manufacturer.setCountry("Germany");
        manufacturerDao.create(manufacturer);
        manufacturer.setName("BMW");
        manufacturer.setCountry("Germany");
        manufacturerDao.create(manufacturer);
        System.out.println("--------------------");

        System.out.println("GetAll manufacturer");
        List<Manufacturer> allManufacturer = manufacturerDao.getAll();
        for (Manufacturer item : allManufacturer) {
            System.out.println(item);
        }
        System.out.println("--------------------");

        System.out.println("Get manufacturer");
        Optional<Manufacturer> optional = manufacturerDao.get(5L);
        System.out.println(optional);
        System.out.println("--------------------");

        System.out.println("Upfate manufacturer");
        Manufacturer updateManufacturer = new Manufacturer();
        updateManufacturer.setId(4L);
        updateManufacturer.setName("Mercedes");
        updateManufacturer.setCountry("Germany");
        System.out.println(manufacturerDao.update(updateManufacturer));
        System.out.println("--------------------");

        System.out.println("Delete manufacturer");
        System.out.println(manufacturerDao.delete(10L));
        System.out.println("--------------------");
    }
}
