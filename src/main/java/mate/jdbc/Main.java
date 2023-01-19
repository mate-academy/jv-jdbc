package mate.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static Manufacturer manufacturer;
    private static final List<Manufacturer> manufacturers = new ArrayList<>();

    static {
        for (int i = 0; i < 10; i++) {
            manufacturer = new Manufacturer();
            manufacturer.setName("Bobby " + i);
            manufacturer.setCountry("Ukraine " + i);
            manufacturers.add(manufacturer);
        }
    }

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        for (Manufacturer m: manufacturers) {
            manufacturerDao.create(m);
        }
        for (Manufacturer manufacturer : manufacturerDao.getAll()) {
            System.out.println(manufacturer.getName());
        }
        Optional<Manufacturer> manufacturerOptional = manufacturerDao.get(5L);
        Optional<Manufacturer> manufacturerOptionalInvalid = manufacturerDao.get(25L);
        System.out.println(manufacturerOptional.get().getName());
        System.out.println(manufacturerOptionalInvalid.isPresent());
    }
}
