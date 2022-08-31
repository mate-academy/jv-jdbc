package mate.jdbc;

import java.util.List;
import mate.jdbc.db.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);

        Manufacturer audi = new Manufacturer();
        audi.setName("Audi");
        audi.setCountry("Germany");
        manufacturerDao.create(audi);

        Manufacturer deLorean = new Manufacturer();
        deLorean.setName("DeLorean Motor Company (DMC)");
        deLorean.setCountry("Ireland");
        manufacturerDao.create(deLorean);
        System.out.println("-----------Audi and DeLorean were added.-------------");

        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        allManufacturers.forEach(System.out::println);
        System.out.println();

        System.out.println("---------DeLorean was deleted (soft-deleted)------------");
        manufacturerDao.delete(deLorean.getId());
        allManufacturers = manufacturerDao.getAll();
        allManufacturers.forEach(System.out::println);
        System.out.println();

        System.out.println("--------Audi was replaced with the Volkswagen Group-----------------");
        audi.setName("the Volkswagen Group");
        manufacturerDao.update(audi);
        List<Manufacturer> newAllManufacturers = manufacturerDao.getAll();
        newAllManufacturers.forEach(System.out::println);
        System.out.println();

        System.out.println("----------Display manufactere with id = 1 -------------------");
        if (manufacturerDao.get(1L).isPresent()) {
            System.out.println(manufacturerDao.get(1L).get());
        }
        System.out.println();

        System.out.println("----------Display manufactere with id = 2 -------------------");
        if (manufacturerDao.get(2L).isPresent()) {
            System.out.println(manufacturerDao.get(2L).get());
        }
        System.out.println();
    }
}
