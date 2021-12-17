package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Porsche");
        manufacturer.setCountry("USA");

        manufacturer = manufacturerDao.create(manufacturer);
        System.out.println(manufacturer);

        boolean isDeleted = manufacturerDao.delete(1L);
        System.out.println(isDeleted);

        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        System.out.println(manufacturers);

        Optional<Manufacturer> optionalManufacturerGetById = manufacturerDao.get(12L);
        Manufacturer manufacturerGetById = optionalManufacturerGetById.orElseThrow(
                () -> new RuntimeException("No such element by id on DB"));
        System.out.println(manufacturerGetById);

        manufacturerDao.update(new Manufacturer(2L, "Lada", "Ukraine"));
        Manufacturer manufacturerUpdated
                = manufacturerDao.update(new Manufacturer(2L, "Lada", "Ukraine"));
        System.out.println(manufacturerUpdated);
    }
}
