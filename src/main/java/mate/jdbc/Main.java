package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

import java.util.Optional;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer hp = new Manufacturer();                    //1 - HP - USA - 0
        hp.setName("HP");
        hp.setCountry("USA");
        manufacturerDao.create(hp);

        Manufacturer toshiba = new Manufacturer();               //2 - Toshiba - Holland - 0
        toshiba.setName("Toshiba");
        toshiba.setCountry("Holland");
        manufacturerDao.create(toshiba);

        Manufacturer lenovo = new Manufacturer();               //3 - Lenovo - China - 0
        lenovo.setName("Lenovo");
        lenovo.setCountry("China");
        manufacturerDao.create(lenovo);

        Manufacturer asus = new Manufacturer();                 //4 - Asus - Taiwan - 0
        asus.setName("Asus");
        asus.setCountry("Taiwan");
        manufacturerDao.create(asus);

        Optional<Manufacturer> x = manufacturerDao.get(4L);     //4 - Asus - Taiwan - 0
        System.out.println(x.toString());

        Optional<Manufacturer> empty = manufacturerDao.get(0L);     //Optional.empty
        System.out.println(empty.toString());

        System.out.println();

        manufacturerDao.getAll().forEach(System.out::println);
        //1 - HP - USA - 0
        //2 - Toshiba - Holland - 0
        //3 - Lenovo - China - 0
        //4 - Asus - Taiwan - 0

        System.out.println();

        Manufacturer asus2 = new Manufacturer();                 //4 - Asus - Taiwan - 0
        asus2.setName("asus2Name");
        asus2.setCountry("asus2Country");
        manufacturerDao.create(asus2);
        System.out.println("asus2 before update:");
        System.out.println(asus2.getId());
        System.out.println(asus2.getName());
        System.out.println(asus2.getCountry());
        asus2 = manufacturerDao.update(asus);
        System.out.println("asus2 after update:");
        System.out.println(asus2.getId());
        System.out.println(asus2.getName());
        System.out.println(asus2.getCountry());

        System.out.println();

        System.out.println(manufacturerDao.delete(asus2.getId()));
    }
}
