package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static final ManufacturerDao manufacturerDao =
            (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

    public static void main(String[] args) {
        manufacturerDao.clearTable();

        Manufacturer apple = new Manufacturer();
        apple.setName("APPLE");
        apple.setCountry("USA");

        Manufacturer xiomi = new Manufacturer();
        xiomi.setName("XIOMI");
        xiomi.setCountry("CHINA");

        System.out.println("----Save----");
        Manufacturer savedApple = manufacturerDao.create(apple);
        Manufacturer savedXiomi = manufacturerDao.create(xiomi);
        System.out.println(savedApple);
        System.out.println(savedXiomi);
        System.out.println();

        System.out.println("----Update----");
        System.out.println("Manufacturer before updating: " + savedApple);
        savedApple.setCountry("Ukraine");
        Manufacturer updatedApple = manufacturerDao.update(savedApple);
        System.out.println(updatedApple);
        System.out.println();

        System.out.println("---Delete----");
        System.out.println("DB before deleting");
        System.out.println(manufacturerDao.getAll());
        System.out.println("DB after deleting");
        System.out.println(manufacturerDao.getAll());
        System.out.println();

        System.out.println("----Get----");
        System.out.println(manufacturerDao.get(updatedApple.getId()));
    }
}
