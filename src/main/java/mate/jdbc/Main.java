package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        System.out.println("App.start");
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        manufacturers.forEach(System.out::println);
        Manufacturer firstInputManufacturer = new Manufacturer("IBM2","USA");
        Manufacturer testManufacturer = manufacturerDao.create(firstInputManufacturer);
        System.out.print("\n" + firstInputManufacturer + " -> ");
        System.out.println(manufacturerDao.get(testManufacturer.getId()).get());
        Manufacturer secondInputManufacturer = new Manufacturer("BMV2","Germany");
        testManufacturer = manufacturerDao.create(secondInputManufacturer);
        System.out.print(secondInputManufacturer + " -> ");
        System.out.println(manufacturerDao.get(testManufacturer.getId()).get());
        System.out.print("\nNow update it to -> ");
        Manufacturer updatebleManufacturer = testManufacturer;
        updatebleManufacturer.setName("VW");
        manufacturerDao.update(updatebleManufacturer);
        System.out.println(manufacturerDao.get(updatebleManufacturer.getId()).get());
        System.out.println("\nNow delete Manufacturer id = "
                + testManufacturer.getId() + "  from DB ");
        if (manufacturerDao.delete(testManufacturer.getId())) {
            System.out.println("Manufacturer id = " + testManufacturer.getId()
                    + " deleted successfully");
        }
        manufacturers = manufacturerDao.getAll();
        manufacturers.forEach(System.out::println);
        System.out.println("App.finish");
    }
}
