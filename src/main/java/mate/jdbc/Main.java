package mate.jdbc;

import dao.ManufacturerDao;
import dao.impl.ManufacturerDaoImpl;
import java.util.List;
import model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
        List<Manufacturer> allManufacturer = manufacturerDao.getAll();
        for (Manufacturer manufacturer : allManufacturer) {
            System.out.println(manufacturer);
        }
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("BMW");
        manufacturer.setCountry("Germani");
        Manufacturer savedManufacturer = manufacturerDao.create(manufacturer);
        System.out.println(savedManufacturer);
        //System.out.println(manufacturerDao.delete(SavedManufacturer.getId()));
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
