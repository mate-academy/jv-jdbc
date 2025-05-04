package mate.jdbc;

import dao.ManufacturerDao;
import java.util.List;
import mate.jdbc.lib.Injector;
import models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("dao");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer autoLux = new Manufacturer("auto_lux", "Ukraine");
        Manufacturer euroLines = new Manufacturer("european_lines", "Austria");
        Manufacturer elite = new Manufacturer("elite", "France");
        autoLux = manufacturerDao.create(autoLux);
        euroLines = manufacturerDao.create(euroLines);
        elite = manufacturerDao.create(elite);

        if (manufacturerDao.get(autoLux.getId()).isPresent()) {
            System.out.println(manufacturerDao.get(autoLux.getId()).get());
        }

        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        for (Manufacturer manufacturer : manufacturers) {
            System.out.println(manufacturer);
        }

        elite.setName("ford-taxi");
        elite.setCountry("England");
        manufacturerDao.update(elite);

        if (manufacturerDao.get(elite.getId()).isPresent()) {
            System.out.println(manufacturerDao.get(elite.getId()).get());
        }

        manufacturerDao.delete(euroLines.getId());
        manufacturers = manufacturerDao.getAll();
        for (Manufacturer manufacturer : manufacturers) {
            System.out.println(manufacturer);
        }
    }
}
