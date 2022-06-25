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
        Manufacturer manufacturer1 = new Manufacturer("auto_lux", "Ukraine");
        Manufacturer manufacturer2 = new Manufacturer("european_lines", "Austria");
        Manufacturer manufacturer3 = new Manufacturer("elite", "France");
        manufacturer1 = manufacturerDao.create(manufacturer1);
        manufacturer2 = manufacturerDao.create(manufacturer2);
        manufacturer3 = manufacturerDao.create(manufacturer3);

        if (manufacturerDao.get(manufacturer1.getId()).isPresent()) {
            System.out.println(manufacturerDao.get(manufacturer1.getId()).get());
        }

        List<Manufacturer> list = manufacturerDao.getAll();
        for (Manufacturer manufacturer : list) {
            System.out.println(manufacturer);
        }

        manufacturer3.setName("ford-taxi");
        manufacturer3.setCountry("England");
        manufacturerDao.update(manufacturer3);

        if (manufacturerDao.get(manufacturer3.getId()).isPresent()) {
            System.out.println(manufacturerDao.get(manufacturer3.getId()).get());
        }

        manufacturerDao.delete(manufacturer2.getId());
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        for (Manufacturer manufacturer : manufacturers) {
            System.out.println(manufacturer);
        }
    }
}
