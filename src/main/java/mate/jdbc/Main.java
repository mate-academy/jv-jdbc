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
        //test create
        System.out.println(System.lineSeparator() + "create:");
        Manufacturer manufacturer = new Manufacturer("SKODA", "Czech Republic");
        System.out.println(manufacturerDao.create(manufacturer));
        List<Manufacturer> listWithNewElement = manufacturerDao.getAll();
        listWithNewElement.forEach(System.out::println);
        //test get
        System.out.println(System.lineSeparator() + "get:");
        System.out.println(manufacturerDao.get(manufacturer.getId()));
        //test update
        System.out.println(System.lineSeparator() + "update:");
        Manufacturer manufacturerUpdate = new Manufacturer(manufacturer.getId(),"BMW", "Germany");
        manufacturerDao.update(manufacturerUpdate);
        List<Manufacturer> listWithUpdateElement = manufacturerDao.getAll();
        listWithUpdateElement.forEach(System.out::println);
        //test delete
        System.out.println(System.lineSeparator() + "delete:");
        System.out.println(manufacturerDao.delete(manufacturer.getId()));
        List<Manufacturer> listWithoutNewElement = manufacturerDao.getAll();
        listWithoutNewElement.forEach(System.out::println);
        //test getAll
        System.out.println(System.lineSeparator() + "getAll:");
        List<Manufacturer> allManufacturer = manufacturerDao.getAll();
        allManufacturer.forEach(System.out::println);
    }
}
