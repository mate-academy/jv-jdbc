package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Volkswagen");
        manufacturer.setCountry("Germany");
        Manufacturer createdManufacturer = manufacturerDao.create(manufacturer);
        System.out.println(createdManufacturer);

        Optional<Manufacturer> getManufacturer = manufacturerDao.get(manufacturer.getId());
        System.out.println(getManufacturer);

        manufacturer.setName("Mitsubishi");
        Manufacturer updatedManufacturer = manufacturerDao.update(manufacturer);
        System.out.println(updatedManufacturer);

        boolean deletedManufacturer = manufacturerDao.delete(manufacturer.getId());
        System.out.println(deletedManufacturer);

        System.out.println(manufacturerDao.getAll());
    }

}
