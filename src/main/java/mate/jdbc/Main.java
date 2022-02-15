package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer("Saturn", "China");
        Manufacturer manufacturer2 = manufacturerDao.get(2L).orElseThrow();
        manufacturer2.setCountry("Ukraine");
        manufacturerDao.update(manufacturer2);
        for (Manufacturer m : manufacturerDao.getAll()) {
            System.out.println(m);
        }
    }
}
