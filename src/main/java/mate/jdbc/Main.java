package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer manufacturerToCreate = new Manufacturer();
        manufacturerToCreate.setName("createdName");
        manufacturerToCreate.setCountry("createdCountry");
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        System.out.println("All manufacturers:");
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(System.lineSeparator());
        manufacturerDao.create(manufacturerToCreate);
        System.out.println("All manufacturers after insertion:");
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(System.lineSeparator());
        System.out.println("Created manufacturer:");
        System.out.println(manufacturerDao.get(
                manufacturerToCreate.getId()).orElseThrow() + System.lineSeparator());
        manufacturerToCreate.setCountry("updatedCountry");
        System.out.println("Updated manufacturer:");
        System.out.println(manufacturerDao.update(
                manufacturerToCreate) + System.lineSeparator());
        System.out.println("Was removing successful?");
        System.out.println(manufacturerDao.delete(
                manufacturerToCreate.getId()) + System.lineSeparator());
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        System.out.println("All manufacturers after removing:");
        manufacturers.forEach(System.out::println);
    }
}
