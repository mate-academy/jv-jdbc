package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");
    private static final String MANUFACTURER_NAME = "Honda";
    private static final String NEW_MANUFACTURER_NAME = "Subaru";
    private static final String MANUFACTURER_COUNTRY = "Japan";
    private static final long INDEX_TO_DELETE = 15L;

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(MANUFACTURER_NAME);
        manufacturer.setCountry(MANUFACTURER_COUNTRY);
        manufacturer = manufacturerDao.create(manufacturer);
        manufacturer.setName(NEW_MANUFACTURER_NAME);
        manufacturerDao.update(manufacturer);
        manufacturerDao.delete(INDEX_TO_DELETE);
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        System.out.println("All manufacturers: ");
        manufacturers.forEach(System.out::println);
        Manufacturer lastManufacturer = manufacturerDao.get(manufacturer.getId()).get();
        System.out.println("Some manufacturer: " + lastManufacturer);
    }
}
