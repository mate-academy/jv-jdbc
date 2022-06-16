package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static final String PACKAGE_NAME = "mate.jdbc";
    public static final Class<ManufacturerDao> CLASS_NAME = ManufacturerDao.class;

    public static void main(String[] args) {
        Injector injector = Injector.getInstance(PACKAGE_NAME);
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(CLASS_NAME);

        System.out.println("Create: " + "- ".repeat(20));
        Manufacturer newManufacturer = new Manufacturer();
        newManufacturer.setName("Ford").setCountry("USA");
        newManufacturer = manufacturerDao.create(newManufacturer);
        System.out.println(newManufacturer);

        System.out.println("Get: " + "- ".repeat(40));
        Long id = newManufacturer.getId();
        Manufacturer manufacturerFromDB = manufacturerDao.get(id).orElseThrow(() ->
                new DataProcessingException("Can't get record from table manufacturers with id="
                        + id));
        System.out.println(manufacturerFromDB);

        System.out.println("Update: " + "- ".repeat(40));
        String oldName = manufacturerFromDB.getName();
        manufacturerFromDB.setName(oldName + "!");
        Manufacturer updatedManufacturer = manufacturerDao.update(manufacturerFromDB);
        System.out.println(updatedManufacturer);

        System.out.println("Delete: " + "- ".repeat(40));
        System.out.println(manufacturerDao.delete(id));

        System.out.println("GetAll: " + "- ".repeat(40));
        List<Manufacturer> manufacturerList = manufacturerDao.getAll();
        manufacturerList.forEach(System.out::println);
    }
}
