package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer hp = new Manufacturer("HP", "USA"); //1 - HP - USA - 0
        manufacturerDao.create(hp);

        Manufacturer toshiba = new Manufacturer("Toshiba", "Holland"); //2 - Toshiba - Holland - 0
        manufacturerDao.create(toshiba);

        Manufacturer lenovo = new Manufacturer("Lenovo", "China"); //3 - Lenovo - China - 0
        manufacturerDao.create(lenovo);

        Manufacturer asus = new Manufacturer("Asus", "Taiwan"); //4 - Asus - Taiwan - 0
        manufacturerDao.create(asus);

        Optional<Manufacturer> fourthRow = manufacturerDao.get(4L); //4 - Asus - Taiwan - 0
        System.out.println(fourthRow.toString());

        Optional<Manufacturer> empty = manufacturerDao.get(0L); //Optional.empty
        System.out.println(empty.toString());

        System.out.println();

        manufacturerDao.getAll().forEach(System.out::println);
        //1 - HP - USA - 0
        //2 - Toshiba - Holland - 0
        //3 - Lenovo - China - 0
        //4 - Asus - Taiwan - 0

        System.out.println();

        Manufacturer asus2 = new Manufacturer("asus2Name", "asus2Country");
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
