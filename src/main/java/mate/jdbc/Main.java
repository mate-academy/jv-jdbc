package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Chevrolet");
        manufacturer.setCountry("USA");
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
