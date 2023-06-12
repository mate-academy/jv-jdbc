package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector INJECTOR
            = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) INJECTOR
                .getInstance(ManufacturerDao.class);
        Manufacturer audi = new Manufacturer("Audi", "Germany");
        manufacturerDao.create(audi);
        printAll(manufacturerDao);
        System.out.println("Printed all manufacturers, after add: " + audi + "\n");

        Manufacturer lexus = new Manufacturer("Lexus", "Japan");
        manufacturerDao.create(lexus);
        printAll(manufacturerDao);
        System.out.println("Printed all manufacturers, after add: " + lexus + "\n");

        Manufacturer toyota = new Manufacturer("Toyota", "Japan");
        manufacturerDao.create(toyota);
        System.out.println(manufacturerDao.get(lexus.getId()));
        System.out.println("Printed manufacturer, after get id: " + lexus.getId() + ". \n");

        manufacturerDao.delete(toyota.getId());
        printAll(manufacturerDao);
        System.out.println("Printed all manufacturers, after delete id:"
                + toyota.getId() + ". \n");

        toyota.setCountry("Ukraine");
        manufacturerDao.update(toyota);
        printAll(manufacturerDao);
        System.out.println("Printed all manufacturers, after update " + toyota);
    }

    private static void printAll(ManufacturerDao manufacturerDao) {
        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        allManufacturers.forEach(System.out::println);
    }
}
