package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer toyota = new Manufacturer("toyota","japan");
        Manufacturer honda = new Manufacturer("honda","japan");
        Manufacturer mercedes = new Manufacturer("mercedes","german");
        //manufacturerDao.create(toyota);
        //manufacturerDao.create(honda);
        //manufacturerDao.create(mercedes);
        System.out.println(manufacturerDao.get(manufacturerDao.create(toyota).getId()));
        System.out.println(manufacturerDao.getAll());
        Optional<Manufacturer> test = manufacturerDao.get(2L);
        System.out.println();
        Manufacturer ferrari = new Manufacturer();
        ferrari.setId(2L);
        ferrari.setName("ferrai");
        ferrari.setCountry("italy");
        //manufacturerDao.update(ferrari);

    }
}
