package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final String YOUR_PACKAGE = "mate.jdbc";
    private static final Injector injector = Injector.getInstance(YOUR_PACKAGE);

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Mitsubishi");
        manufacturer.setCountry("Japan");
        Manufacturer resultManufacturer = manufacturerDao.create(manufacturer);
        System.out.println("Create: ");
        System.out.println(resultManufacturer);

        System.out.println("Get by id: ");
        Optional<Manufacturer> optionalManufacturer =
                manufacturerDao.get(resultManufacturer.getId());
        optionalManufacturer.ifPresent(System.out::println);

        System.out.println("Get all: ");
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        for (Manufacturer mnfctr : manufacturers) {
            System.out.println(mnfctr);
        }

        System.out.println("Update:");
        Manufacturer manufacturerForUpdate = new Manufacturer();
        manufacturerForUpdate.setId(1L);
        manufacturerForUpdate.setName("mercedes");
        manufacturerForUpdate.setCountry("America");
        manufacturerDao.update(manufacturerForUpdate);

        System.out.println("Delete: ");
        boolean isDeleted = manufacturerDao.delete(manufacturer.getId());
        System.out.println(isDeleted);
    }
}
