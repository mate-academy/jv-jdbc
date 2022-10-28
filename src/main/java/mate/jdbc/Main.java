package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer bmwManufacturer = new Manufacturer();
        bmwManufacturer.setName("bmw");
        bmwManufacturer.setCountry("germany");
        manufacturerDao.create(bmwManufacturer);

        Manufacturer mazdaManufacturer = new Manufacturer();
        mazdaManufacturer.setName("mazda");
        mazdaManufacturer.setCountry("japan");
        manufacturerDao.create(mazdaManufacturer);

        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        allManufacturers.forEach(System.out::println);

        Optional<Manufacturer> manufacturer = manufacturerDao.get(2L);
        System.out.println(manufacturer.get());

        Manufacturer bmwChinaManufacturer = new Manufacturer();
        bmwChinaManufacturer.setId(1L);
        bmwChinaManufacturer.setName("bmwCh");
        bmwChinaManufacturer.setCountry("china");
        manufacturerDao.update(bmwChinaManufacturer);

        manufacturerDao.delete(1L);
    }
}
