package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer bugatti = new Manufacturer(null,"Bugatti", "France");
        Manufacturer rollsRoyce = new Manufacturer(null,"Rolls-Royce", "England");
        Manufacturer ford = new Manufacturer(null,"Ford", "USA");
        bugatti = manufacturerDao.create(bugatti);
        rollsRoyce = manufacturerDao.create(rollsRoyce);
        ford = manufacturerDao.create(ford);
        System.out.println("*************************************************");
        System.out.println("*             Create 3 manufacturers            *");
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("*************************************************");
        System.out.println("*   Change name manufacturer Ford to Cadillac   *");
        ford.setName("Cadillac");
        manufacturerDao.update(ford);
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("*************************************************");
        System.out.println("* Delete manufacturer Rolls-Royce from database *");
        manufacturerDao.delete(rollsRoyce.getId());
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("*************************************************");
        System.out.println("*      Get manufacturer Bugatti from database      *");
        Optional<Manufacturer> getManufacturer = manufacturerDao.get(bugatti.getId());
        System.out.println(getManufacturer);
    }
}
