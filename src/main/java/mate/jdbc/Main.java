package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final String ROOT_PACKAGE = "mate.jdbc";
    private static final Injector injector = Injector.getInstance(ROOT_PACKAGE);

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                        .getInstance(ManufacturerDao.class);

        Manufacturer manufacturer = Manufacturer.builder()
                .country("Ukraine")
                .name("Zaporozhets")
                .build();

        manufacturer = manufacturerDao.create(manufacturer);
        manufacturer = manufacturerDao.getById(manufacturer.getId()).orElse(new Manufacturer());
        manufacturer = manufacturerDao.update(manufacturer);
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        boolean isDeleted = manufacturerDao.delete(manufacturer.getId());
    }
}
