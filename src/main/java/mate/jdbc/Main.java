package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer firstManufacturer = new Manufacturer();
        firstManufacturer.setName("firstName");
        firstManufacturer.setCountry("firstCountry");
        Manufacturer secondManufacturer = new Manufacturer();
        secondManufacturer.setName("secondName");
        secondManufacturer.setCountry("secondCountry");
        Manufacturer thirdManufacturer = new Manufacturer();
        thirdManufacturer.setName("thirdName");
        thirdManufacturer.setCountry("thirdCountry");

        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        System.out.println("-create method-");
        System.out.println(manufacturerDao.create(firstManufacturer));
        System.out.println(manufacturerDao.create(secondManufacturer));
        System.out.println(manufacturerDao.create(thirdManufacturer));

        System.out.println("-get method-");
        System.out.println(manufacturerDao.get(firstManufacturer.getId()));

        System.out.println("-getAll method-");
        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        allManufacturers.forEach(System.out::println);

        secondManufacturer.setName("updatedName");
        manufacturerDao.update(secondManufacturer);
        manufacturerDao.delete(thirdManufacturer.getId());

        System.out.println("-getAll method after update and delete-");
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
