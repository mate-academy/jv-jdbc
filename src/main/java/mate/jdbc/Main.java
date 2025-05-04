package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer manufacturerMers = new Manufacturer();
        manufacturerMers.setName("Mercedes");
        manufacturerMers.setCountry("Germany");
        Manufacturer manufacturerBmv = new Manufacturer();
        manufacturerBmv.setName("Bmv");
        manufacturerBmv.setCountry("Germany");
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer manufacturer1 = manufacturerDao.create(manufacturerMers);
        Manufacturer manufacturer2 = manufacturerDao.create(manufacturerBmv);
        System.out.println(manufacturer1);
        System.out.println(manufacturer2);

        List<Manufacturer> all = manufacturerDao.getAll();
        System.out.println(all);

        Optional<Manufacturer> manufacturerOpt = manufacturerDao.get(2L);
        System.out.println(manufacturerOpt);

        manufacturerBmv.setName("Opel");
        Manufacturer manufacturer2Update = manufacturerDao.update(manufacturerBmv);
        System.out.println(manufacturer2Update);

        boolean delete = manufacturerDao.delete(1L);
        System.out.println(delete);
        System.out.println(manufacturerDao.getAll());
    }
}
