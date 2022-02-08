package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer firstManufacturer = new Manufacturer();
        firstManufacturer.setName("first");
        firstManufacturer.setCountry("Ukraine");

        Manufacturer secondManufacturer = new Manufacturer();
        secondManufacturer.setName("second");
        secondManufacturer.setCountry("Italy");

        Manufacturer thirdManufacturer = new Manufacturer();
        thirdManufacturer.setName("third");
        thirdManufacturer.setCountry("Spain");

        List<Manufacturer> manufacturersList = List.of(firstManufacturer,
                secondManufacturer, thirdManufacturer);
        for (Manufacturer manufacturer : manufacturersList) {
            manufacturerDao.create(manufacturer);
        }

        System.out.println("------Get all-----");
        printAll(manufacturerDao);

        System.out.println("------Get by id-----");
        System.out.println(manufacturerDao.get(3L));

        System.out.println("------Update-----");
        Manufacturer secondManufacturerUpdate = manufacturersList.get(2);

        secondManufacturerUpdate.setName("second Update");
        manufacturerDao.update(secondManufacturerUpdate);
        printAll(manufacturerDao);

        System.out.println("------Deleted-----");
        manufacturerDao.delete(1L);
        manufacturerDao.delete(3L);
        printAll(manufacturerDao);
    }

    private static void printAll(ManufacturerDao manufacturerDao) {
        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        allManufacturers.forEach(System.out::println);
    }
}
