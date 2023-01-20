package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static final long EXISTING_ID = 1L;
    private static final long  NON_EXISTING_ID = 2L;


    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("BMW");
        manufacturer.setCountry("Germany");
        manufacturerDao.create(manufacturer);
        manufacturerDao.get(manufacturer.getId());
        manufacturer.setId(EXISTING_ID);
        manufacturer.setName("Lexus");
        manufacturer.setCountry("Japan");
        manufacturerDao.update(manufacturer);
        manufacturerDao.delete(NON_EXISTING_ID);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
