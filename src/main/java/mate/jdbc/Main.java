package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        final ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);

        System.out.println("Create(): ");
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Volvo");
        manufacturer.setCountry("Germany");
        Manufacturer createdManufacturer = manufacturerDao.create(manufacturer);
        System.out.println(createdManufacturer);

        System.out.println("Get(): ");
        Optional<Manufacturer> getManufacturer = manufacturerDao.get(3L);
        System.out.println(getManufacturer);

        System.out.println("Update(): ");
        manufacturer.setName("Toyota");
        Manufacturer updatedManufacturer = manufacturerDao.update(manufacturer);
        System.out.println(updatedManufacturer);

        System.out.println("Delete(): ");
        boolean deletedManufacturer = manufacturerDao.delete(3L);
        System.out.println(deletedManufacturer);

        System.out.println("GetAll(): ");
        System.out.println(manufacturerDao.getAll());
    }
}
