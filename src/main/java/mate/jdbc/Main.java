package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.impl.ManufacturerDaoImpl;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Chevrolet");
        manufacturer.setCountry("USA");
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
        manufacturer = manufacturerDao.create(manufacturer);
        manufacturer.setName("Chevrolet Cruze");
        manufacturerDao.update(manufacturer);
        manufacturerDao.delete(12L);
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        System.out.println("All manufacturers: ");
        manufacturers.forEach(System.out::println);
        Manufacturer firstManufacturer = manufacturerDao.get(manufacturer.getId()).get();
        System.out.println("Some manufacturer: " + firstManufacturer);
    }
}
