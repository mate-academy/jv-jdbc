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
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Ford");
        manufacturer.setCountry("USA");
        System.out.println(manufacturer = manufacturerDao.create(manufacturer));
        manufacturer.setName("Chevrolet");
        System.out.println(manufacturerDao.update(manufacturer));
        System.out.println(manufacturerDao.get(1L).toString());
        manufacturerDao.delete(3L);
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        manufacturers.forEach(m -> System.out.println(m.toString()));
    }
}
