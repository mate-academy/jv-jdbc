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
    private static final Long VALID_ID = 5L;
    private static final Long INVALID_ID = 5L;

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
        for (Manufacturer manufacturer: manufacturers) {
            manufacturerDao.create(manufacturer);
        }
        for (Manufacturer manufacturer : manufacturerDao.getAll()) {
            System.out.println(manufacturer.getName());
        }
        Optional<Manufacturer> manufacturerOptional = manufacturerDao.get(VALID_ID);
        Optional<Manufacturer> manufacturerOptionalInvalid = manufacturerDao.get(INVALID_ID);
        System.out.println(manufacturerOptional.get().getName());
        System.out.println(manufacturerOptionalInvalid.isPresent());
    }
}
