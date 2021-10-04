package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer bmwManufacturer = new Manufacturer();
        bmwManufacturer.setName("BMW");
        bmwManufacturer.setCountry("Germany");
        Manufacturer createdBmwManufacturer = manufacturerDao.create(bmwManufacturer);
        System.out.println(createdBmwManufacturer);
        Manufacturer mercedesManufacturer = new Manufacturer();
        mercedesManufacturer.setName("Mercedes");
        mercedesManufacturer.setCountry("Germany");
        Manufacturer createdMercedesManufacturer = manufacturerDao.create(mercedesManufacturer);
        System.out.println(createdMercedesManufacturer);
        Optional<Manufacturer> optionalManufacturer =
                manufacturerDao.get(createdBmwManufacturer.getId());
        optionalManufacturer.ifPresent(System.out::println);
        List<Manufacturer> manufacturersList = manufacturerDao.getAll();
        for (Manufacturer manufacturers : manufacturersList) {
            System.out.println(manufacturers);
        }
        Manufacturer updatedManufacturer = new Manufacturer();
        updatedManufacturer.setId(createdBmwManufacturer.getId());
        updatedManufacturer.setName("Ferrari ");
        updatedManufacturer.setCountry("Italy");
        System.out.println(manufacturerDao.update(updatedManufacturer));
    }
}
