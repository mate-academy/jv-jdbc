package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static final ManufacturerDao manufacturerDao =
            (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

    public static void main(String[] args) {
        System.out.println("Create:");
        Manufacturer opelManufacturer = new Manufacturer("Opel", "Germany");
        Manufacturer fiatManufacturer = new Manufacturer("Fiat", "France");
        System.out.println(manufacturerDao.create(opelManufacturer));
        System.out.println(manufacturerDao.create(fiatManufacturer));

        System.out.println("Read:");
        System.out.println(manufacturerDao.get(fiatManufacturer.getId()));

        System.out.println("Update:");
        fiatManufacturer.setName("Citroen");
        System.out.println(manufacturerDao.update(fiatManufacturer));

        System.out.println("Delete:");
        System.out.println(manufacturerDao.delete(fiatManufacturer.getId()));

        System.out.println("Get all:");
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        manufacturers.forEach(System.out::println);
    }
}
