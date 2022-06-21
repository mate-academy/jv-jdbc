package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer volvo = Manufacturer.of("Volvo", "Sweden");
        Manufacturer renault = Manufacturer.of("Renault","France");
        Manufacturer volkswagen = Manufacturer.of("Volkswagen", "undefined");
        Manufacturer volkswagenNormal = Manufacturer.of(3L, "Volkswagen", "Germany");

        manufacturerDao.create(volvo);
        manufacturerDao.create(renault);
        manufacturerDao.create(volkswagen);
        manufacturerDao.get(2L);
        manufacturerDao.getAll();
        manufacturerDao.update(volkswagenNormal);
        manufacturerDao.delete(3L);
    }
}
