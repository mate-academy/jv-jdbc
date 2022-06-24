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
        Manufacturer manufacturer1 = new Manufacturer(1L, "auto_lux", "Ukraine");
        Manufacturer manufacturer2 = new Manufacturer(1L, "european_lines", "Austria");
        Manufacturer manufacturer3 = new Manufacturer(1L, "elite", "France");
        manufacturerDao.create(manufacturer1);
        manufacturerDao.create(manufacturer2);
        manufacturerDao.create(manufacturer3);

        if (manufacturerDao.get(3L).isPresent()) {
            System.out.println(manufacturerDao.get(3L).get());
        }

        List<Manufacturer> list = manufacturerDao.getAll();
        for (Manufacturer manufacturer : list) {
            System.out.println(manufacturer);
        }

        Manufacturer newDataManufacturer = new Manufacturer(1L, "Shara", "Ukraine");
        manufacturerDao.update(newDataManufacturer);

        if (manufacturerDao.get(1L).isPresent()) {
            System.out.println(manufacturerDao.get(1L).get());
        }

        manufacturerDao.delete(3L);
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        for (Manufacturer manufacturer : manufacturers) {
            System.out.println(manufacturer);

        }
    }
}
