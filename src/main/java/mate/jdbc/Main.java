package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer helena = new Manufacturer();
        helena.setName("Helena");
        helena.setCountry("Austria");
        manufacturerDao.create(helena);
        System.out.println("Get All manufacturers : ");
        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        for (Manufacturer manufacturer: allManufacturers) {
            System.out.println(manufacturer);
        }
        System.out.println();
        System.out.println("Create manufacturer : name - Huan, country - Vietnam");
        Manufacturer huan = new Manufacturer();
        huan.setCountry("Vietnam");
        huan.setName("Huan");
        manufacturerDao.create(huan);
        System.out.println("Delete manufacturer : name - Helena, country - Austria");
        manufacturerDao.delete(helena.getId());
        System.out.println("Get manufacturer by id: " + 1L);
        System.out.println(manufacturerDao.get(1L));
        System.out.println("Update manufacturer : Huan");
        huan.setCountry("Spain");
        manufacturerDao.update(huan);
        System.out.println();
        System.out.println("Get All manufacturers : ");
        allManufacturers = manufacturerDao.getAll();
        for (Manufacturer manufacturer: allManufacturers) {
            System.out.println(manufacturer);
        }
    }
}
