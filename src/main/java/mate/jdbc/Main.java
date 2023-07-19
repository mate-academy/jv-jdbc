package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDaoImpl) injector.getInstance(ManufacturerDao.class);

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Mercedes");
        manufacturer.setCountry("Germany");

        Manufacturer createdManufacturer = manufacturerDao.create(manufacturer);
        System.out.println("Created Manufacturer name: " + createdManufacturer.getName()
                + " ,country: " + createdManufacturer.getCountry());

        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        System.out.println("All manufacturers:");
        for (Manufacturer manufacturer1 : allManufacturers) {
            System.out.print("Manufacturer: [" + "name: "
                    + manufacturer1.getName()
                    + " ,country: " + manufacturer1.getCountry() + "]" + "\n");
        }

        manufacturer.setName("BMW");
        manufacturer.setCountry("Germany");
        Manufacturer updatedManufacturer = manufacturerDao.update(manufacturer);
        System.out.println("Updated Manufacturer: "
                + "[name: " + updatedManufacturer.getName()
                + " country: " + manufacturer.getCountry() + "]");

        boolean isDeleted = manufacturerDao.delete(manufacturer.getId());
        System.out.println("Manufacturer deleted: " + isDeleted);

        Optional<Manufacturer> manufacturerOptional =
                manufacturerDao.get(createdManufacturer.getId());
        manufacturerOptional.ifPresent(System.out::println);
    }
}
