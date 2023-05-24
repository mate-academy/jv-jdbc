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
 //     Test Insert first Manufacturer to DB
        Manufacturer firstInputManufacturer = new Manufacturer();
        firstInputManufacturer.setName("IBM");
        firstInputManufacturer.setCountry("USA");
        Manufacturer testManufacturer = manufacturerDao.create(firstInputManufacturer);
 //     Test get new first Manufacturer from DB
        System.out.println(manufacturerDao.get(testManufacturer.getId()).get());
  //    Test insert second Manufacturer to DB
        Manufacturer secondInputManufacturer = new Manufacturer();
        secondInputManufacturer.setName("BMV");
        secondInputManufacturer.setCountry("Germany");
        testManufacturer = manufacturerDao.create(secondInputManufacturer);
  //   Test select All Manufacturers from DB
        List<Manufacturer> list = manufacturerDao.getAll();
        list.forEach(System.out::println);
        System.out.println("\n");
  //    Test get new second item from DB
        System.out.println(manufacturerDao.get(testManufacturer.getId()).get().toString());
        System.out.println("\n -> ");
  //    Test Update Manufacturer in DB
        secondInputManufacturer.setName("VW");
        manufacturerDao.update(testManufacturer);
  //    Verify update result
        System.out.println(manufacturerDao.get(testManufacturer.getId()).get().toString());
        System.out.println("\n");
   //   Test delete in DB
        manufacturerDao.delete(testManufacturer.getId());
   //   Verify delete result
        list = manufacturerDao.getAll();
        list.forEach(System.out::println);
        System.out.println("App.finish");
    }
}
