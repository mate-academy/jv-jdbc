package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        // test method getAll()
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        List<Manufacturer> all = manufacturerDao.getAll();
        System.out.println("All: ");
        for (Manufacturer m : all) {
            System.out.println(m);
        }

        // test method create()
        Manufacturer newManufacturer = new Manufacturer();
        newManufacturer.setName("BMW");
        newManufacturer.setCountry("BMW");
        manufacturerDao.create(newManufacturer);

        System.out.println("After create :");
        for (Manufacturer m : manufacturerDao.getAll()) {
            System.out.println(m);
        }

        // test method delete()
        manufacturerDao.delete(newManufacturer.getId());

        // test method update()
        Manufacturer updateManufacturer = new Manufacturer();
        updateManufacturer.setId(1L);
        updateManufacturer.setName("Geely");
        updateManufacturer.setCountry("China");
        manufacturerDao.update(updateManufacturer);

        //test method get()
        Manufacturer manufacturer = manufacturerDao.get(10L).get();
        System.out.println(manufacturer);
    }
}
