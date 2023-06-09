package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) INJECTOR
                .getInstance(ManufacturerDao.class);
        Manufacturer audi = new Manufacturer("Audi", "Germany");
        manufacturerDao.create(audi);
        printAll(manufacturerDao);
        System.out.println("Printed all manufacturers, after add: " + audi + "\n");

        Manufacturer lamborghini = new Manufacturer("Lamborghini", "Italy");
        manufacturerDao.create(lamborghini);
        printAll(manufacturerDao);
        System.out.println("Printed all manufacturers, after add: " + lamborghini + "\n");

        Manufacturer toyota = new Manufacturer("Toyota", "Japan");
        manufacturerDao.create(toyota);
        System.out.println(manufacturerDao.get(lamborghini.getId()));
        System.out.println("Printed manufacturer, after get id: " + lamborghini.getId() + ". \n");

        manufacturerDao.delete(toyota.getId());
        printAll(manufacturerDao);
        System.out.println("Printed all manufacturers, after delete id:"
                + toyota.getId() + ". \n");

        Manufacturer mercedes = new Manufacturer(2L, "Mercedes", "Germany");
        manufacturerDao.update(mercedes);
        printAll(manufacturerDao);
        System.out.println("Printed all manufacturers, after update " + mercedes);
    }

    private static void printAll(ManufacturerDao manufacturerDao) {
        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        allManufacturers.forEach(System.out::println);
    }
}

