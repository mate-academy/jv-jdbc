package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static final ManufacturerDao manufacturerDao =
            (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

    public static void main(String[] args) {
        manufacturerDao.create(Manufacturer.builder().name("randomName").country("randomCountry")
                .build());
        manufacturerDao.get(1L);
        manufacturerDao.update(Manufacturer.builder().id(1L).name("updatedName").country(
                "updatedCountry").build());
        manufacturerDao.delete(2L);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
