package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static long ID1 = 1L;
    private static long ID2 = 2L;

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer nissan = new Manufacturer();
        nissan.setName("NISSAN");
        nissan.setCountry("JAPAN");
        Manufacturer savedManufacturer = manufacturerDao.create(nissan);
        Optional<Manufacturer> getFromDb = manufacturerDao.get(ID1);
        Manufacturer manufacturerFromDb = manufacturerDao.get(ID2).orElseGet(Manufacturer::new);
        manufacturerFromDb.setCountry("Japanese");
        Manufacturer updatedManufacturer = manufacturerDao.update(manufacturerFromDb);
        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        manufacturerDao.delete(1L);
        allManufacturers = manufacturerDao.getAll();
        allManufacturers.forEach(System.out::println);
    }
}
