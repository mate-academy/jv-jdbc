package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer manufacturerLinkoln = new Manufacturer();
        manufacturerLinkoln.setName("Lincoln");
        manufacturerLinkoln.setCountry("USA");

        Manufacturer manufacturerFiat = new Manufacturer();
        manufacturerFiat.setName("Fiat");
        manufacturerFiat.setCountry("Italy");

        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer manufacturer1 = manufacturerDao.create(manufacturerLinkoln);
        Manufacturer manufacturer2 = manufacturerDao.create(manufacturerFiat);
        System.out.println(manufacturer1);
        System.out.println(manufacturer2);

        List<Manufacturer> all = manufacturerDao.getAll();
        System.out.println(all);

        Optional<Manufacturer> manufacturerOpt = manufacturerDao.get(2L);
        System.out.println(manufacturerOpt);

        manufacturerFiat.setName("Renault");
        Manufacturer manufacturer2Update = manufacturerDao.update(manufacturerFiat);
        System.out.println(manufacturer2Update);

        boolean delete = manufacturerDao.delete(1L);
        System.out.println(delete);
        System.out.println(manufacturerDao.getAll());
    }
}
