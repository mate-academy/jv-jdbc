package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static final long TEST_ID = 3L;
    private static final Manufacturer CREATE_METHOD_MANUFACTURER
            = new Manufacturer(null, "Opel", "Germany");
    private static final Manufacturer UPDATE_METHOD_MANUFACTURER
            = new Manufacturer(2L, "Hyundai", "South Korea");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer createdManufacturer = manufacturerDao.create(CREATE_METHOD_MANUFACTURER);
        Optional<Manufacturer> manufacturerById = manufacturerDao.get(TEST_ID);
        List<Manufacturer> allManufacturersList = manufacturerDao.getAll();
        Manufacturer updatedManufacturer = manufacturerDao.update(UPDATE_METHOD_MANUFACTURER);
        boolean isDeleted = manufacturerDao.delete(TEST_ID);
    }
}
