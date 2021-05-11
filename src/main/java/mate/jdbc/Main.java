package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static final ManufacturerDao manufacturerDao =
            (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

    public static void main(String[] args) {
        Manufacturer bmwManufacturer = new Manufacturer();
        bmwManufacturer.setName("BMW");
        bmwManufacturer.setCountry("Germany");
        
        Manufacturer mercedesManufacturer = new Manufacturer();
        mercedesManufacturer.setName("Mercedes");
        mercedesManufacturer.setCountry("Germany");
        
        Manufacturer bugattiManufacturer = new Manufacturer();
        bugattiManufacturer.setName("Bugatti");
        bugattiManufacturer.setCountry("France");

        manufacturerDao.create(bmwManufacturer);
        manufacturerDao.create(mercedesManufacturer);
        manufacturerDao.create(bugattiManufacturer);

        System.out.println(manufacturerDao.get(20L));

        manufacturerDao.delete(21L);

        bugattiManufacturer.setName("BugattiNew");
        manufacturerDao.update(bugattiManufacturer);

        List<Manufacturer> manufacturerList = manufacturerDao.getAll();
        manufacturerList.forEach(System.out::println);
    }
}
