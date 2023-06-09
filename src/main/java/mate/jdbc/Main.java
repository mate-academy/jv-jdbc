package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc.dao");
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer createManufacturer = new Manufacturer("Nestle", "Switzerland");
        Manufacturer createdManufacturer = manufacturerDao.create(createManufacturer);
        System.out.println(createdManufacturer);

        Optional<Manufacturer> getManufacturer = manufacturerDao.get(1L);
        System.out.println(getManufacturer);

        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        System.out.println(allManufacturers);

        Optional<Manufacturer> optionalManufacturer = manufacturerDao.get(8L);
        Manufacturer updateManufacturer = optionalManufacturer.get();
        updateManufacturer.setName("Update manufacturer");
        updateManufacturer.setCountry("Update country");
        Manufacturer updatedManufacturer = manufacturerDao.update(updateManufacturer);
        System.out.println(updatedManufacturer);

        boolean deleteManufacturer = manufacturerDao.delete(9L);
        System.out.println(deleteManufacturer);
    }
}
