package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        System.out.println("Create: " + "- ".repeat(20));
        Manufacturer newManufacturer = new Manufacturer();
        newManufacturer.setName("Ford").setCountry("USA");
        newManufacturer = manufacturerDao.create(newManufacturer);
        System.out.println(newManufacturer);

        System.out.println("Get: " + "- ".repeat(40));
        Long id = newManufacturer.getId();
        Optional<Manufacturer> manufacturer = manufacturerDao.get(id);
        manufacturer.ifPresent(System.out::println);

        System.out.println("Update: " + "- ".repeat(40));
        String oldName = newManufacturer.getName();
        newManufacturer.setName(oldName + "!");
        Manufacturer updatedManufacturer = manufacturerDao.update(newManufacturer);
        System.out.println(updatedManufacturer);

        System.out.println("Delete: " + "- ".repeat(40));
        System.out.println(manufacturerDao.delete(id));

        System.out.println("GetAll: " + "- ".repeat(40));
        List<Manufacturer> manufacturerList = manufacturerDao.getAll();
        manufacturerList.forEach(System.out::println);
    }
}
