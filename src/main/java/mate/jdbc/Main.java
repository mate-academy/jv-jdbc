package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Citroen");
        manufacturer.setCountry("France");
        manufacturerDao.create(manufacturer);
        manufacturerDao.delete(1L);
        manufacturer.setId(7L);
        manufacturer.setName("Seat");
        manufacturer.setCountry("Span");
        manufacturerDao.update(manufacturer);
        List<Manufacturer> all = manufacturerDao.getAll();
        all.forEach(System.out::println);
        System.out.println();
        System.out.println(manufacturerDao.get(2L).get());
    }
}

