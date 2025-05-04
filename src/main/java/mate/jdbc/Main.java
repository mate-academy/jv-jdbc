package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer volkswagen = new Manufacturer("Volkswagen", "Germany");
        Manufacturer kia = new Manufacturer("KIA", "South Korea");
        manufacturerDao.create(volkswagen);
        manufacturerDao.create(kia);
        volkswagen.setName("VW");
        kia.setCountry("Korea");
        manufacturerDao.update(volkswagen);
        manufacturerDao.update(kia);
        manufacturerDao.delete(volkswagen.getId());
        manufacturerDao.delete(kia.getId());
        manufacturerDao.get(volkswagen.getId());
        manufacturerDao.getAll();
    }
}
