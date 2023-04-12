package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

import java.util.List;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        //create manufacturers
        Manufacturer skoda = new Manufacturer();
        skoda.setId(1L);
        skoda.setName("Skoda");
        skoda.setCountry("Czech");
        manufacturerDao.create(skoda);
        Manufacturer forb = new Manufacturer();
        forb.setName("Forb");
        forb.setCountry("China");
        manufacturerDao.create(forb);
        Manufacturer mitsubishi = new Manufacturer();
        mitsubishi.setName("Mitsubishi");
        mitsubishi.setCountry("Japan");
        manufacturerDao.create(mitsubishi);
        Manufacturer daewoo = new Manufacturer();
        daewoo.setId(4L);
        daewoo.setName("Daewoo");
        daewoo.setCountry("Korea");
        manufacturerDao.create(daewoo);
        System.out.println("View all our manufacturers please... ");
        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        allManufacturers.forEach(System.out::println);
        //update the manufacturer "Forb", id = 2
        Manufacturer ford = new Manufacturer();
        ford.setId(2L);
        ford.setName("Ford");
        ford.setCountry("USA");
        manufacturerDao.update(ford);
        //check the updated manufacturer
        System.out.println();
        System.out.println("We updated this manufacturer:");
        System.out.println(manufacturerDao.get(2L));
        //call a non-existent manufacturer
        System.out.println();
        System.out.println("Do we have a fifth manufacturer?");
        System.out.println(manufacturerDao.get(5L));
        //remove unnecessary manufacturer
        System.out.println();
        System.out.println("We remove Daewoo cars from our fleet");
        manufacturerDao.delete(4L);
        System.out.println();
        System.out.println("View all our manufacturers again please... ");
        allManufacturers = manufacturerDao.getAll();
        allManufacturers.forEach(System.out::println);
    }
}
