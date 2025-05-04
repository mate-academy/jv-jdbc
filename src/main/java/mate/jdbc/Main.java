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
        Manufacturer bmw = new Manufacturer();
        bmw.setName("BMW");
        bmw.setCountry("Germany");

        Manufacturer renault = new Manufacturer();
        renault.setName("Renault");
        renault.setCountry("Germany");

        Manufacturer unknown = new Manufacturer();
        unknown.setName("Unknown");
        unknown.setCountry("Unknown");

        System.out.println("create manufacturers: ");
        System.out.println(manufacturerDao.create(bmw));
        System.out.println(manufacturerDao.create(renault));
        System.out.println(manufacturerDao.create(unknown));

        manufacturerDao.delete(unknown.getId());
        System.out.println("get bmw: " + manufacturerDao.get(renault.getId()));

        renault.setCountry("France");
        manufacturerDao.update(renault);

        System.out.println("getAll manufacturers: ");
        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        allManufacturers.forEach(System.out::println);
    }
}
