package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate");
    private static final ManufacturerDao manufacturerDao = (ManufacturerDao)
            injector.getInstance(ManufacturerDao.class);
    private static final long SECOND_ID = 2L;
    private static final long FIFTH_ID = 5L;
    private static final String KRAZ_MANUFACTURER = "kraz";
    private static final String COUNTRY = "Ukraine";
    private static final String BOGDAN_MANUFACTURER = "Bogdan";

    public static void main(String[] args) {

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(KRAZ_MANUFACTURER);
        manufacturer.setCountry(COUNTRY);
        manufacturerDao.create(manufacturer);

        manufacturer.setName(BOGDAN_MANUFACTURER);
        manufacturer.setCountry(COUNTRY);
        manufacturerDao.update(manufacturer);

        System.out.println(manufacturer);

        System.out.println(manufacturerDao.get(FIFTH_ID));

        System.out.println(manufacturerDao.delete(SECOND_ID));

        manufacturerDao.getAll().forEach(System.out::println);
    }
}
