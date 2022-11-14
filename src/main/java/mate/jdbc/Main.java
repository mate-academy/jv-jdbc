package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDaoImpl) injector
                .getInstance(ManufacturerDao.class);

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Dodge");
        manufacturer.setCountry("USA");
        Manufacturer dodge = manufacturerDao.create(manufacturer);

        Optional<Manufacturer> optionalManufacturer = manufacturerDao.get(4L);
        System.out.println(optionalManufacturer);

        Manufacturer manufacturer1 = new Manufacturer();
        manufacturer1.setName("Ford");
        manufacturer1.setCountry("USA");
        manufacturer1.setId(6L);
        Manufacturer ford = manufacturerDao.update(manufacturer1);

        boolean deleted = manufacturerDao.delete(7L);
        System.out.println(deleted);

        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        for (Manufacturer m : allManufacturers) {
            System.out.println(m);
        }
    }
}
