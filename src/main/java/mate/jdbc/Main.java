package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();

        /* INSERT to DB */
        manufacturer.setName("Mazda");
        manufacturer.setCountry("Japan");
        Manufacturer savedManufacturer = manufacturerDao.create(manufacturer);
        System.out.println("------------------------------------------");
        System.out.println("Saved Manufacturer: " + savedManufacturer);
        System.out.println("------------------------------------------");

        /* UPDATE in DB */
        manufacturer.setName("Ferrari");
        manufacturer.setCountry("Italia");
        System.out.println("Updated Manufacturer: "
                + manufacturerDao.update(manufacturer));
        System.out.println("------------------------------------------");

        /* SELECT by id from DB */
        System.out.println("Selected by id Manufacturer: "
                + manufacturerDao.get(manufacturer.getId()));
        System.out.println("------------------------------------------");

        /* DELETE from DB */
        System.out.println("Deleted Manufacturer: "
                + manufacturerDao.delete(savedManufacturer.getId()));
        System.out.println("------------------------------------------");

        /* SOUT all Manufacturers from DB */
        System.out.println("List of Data from Base Manufacturer: ");
        System.out.println(" ");
        List<Manufacturer> list = manufacturerDao.getAll();
        for (Manufacturer allManufacturer : list) {
            System.out.println(allManufacturer);
        }
    }
}
