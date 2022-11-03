package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);
        Manufacturer firstManufacturer = new Manufacturer();
        firstManufacturer.setName("audi");
        firstManufacturer.setCountry("Germany");
        manufacturerDao.create(firstManufacturer);
        Manufacturer secondManufacturer = new Manufacturer();
        secondManufacturer.setName("Volkswagen");
        secondManufacturer.setCountry("Germany");
        manufacturerDao.create(secondManufacturer);
        Optional<Manufacturer> manufacturerFromDb = manufacturerDao.get(firstManufacturer.getId());
        System.out.println(manufacturerFromDb);
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        manufacturers.forEach(System.out::println);
        secondManufacturer.setName("subaru");
        secondManufacturer.setCountry("Japan");
        Manufacturer updatedManufacturer = manufacturerDao.update(secondManufacturer);
        Optional<Manufacturer> updatedManufacturerCheck =
                manufacturerDao.get(updatedManufacturer.getId());
        System.out.println(updatedManufacturerCheck);
        manufacturerDao.delete(secondManufacturer.getId());
        List<Manufacturer> manufacturerList = manufacturerDao.getAll();
        manufacturerList.forEach(System.out::println);
    }
}
