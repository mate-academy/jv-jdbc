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
        Manufacturer toyotaFromDB = manufacturerDao.create(toyota);
        Manufacturer hondaFromDB = manufacturerDao.create(honda);
        Manufacturer mercedesFromDB = manufacturerDao.create(mercedes);
        System.out.println(manufacturerDao.get(toyotaFromDB.getId()));
        System.out.println(manufacturerDao.getAll());
        Optional<Manufacturer> test = manufacturerDao.get(hondaFromDB.getId());
        System.out.println(test);
        Manufacturer ferrari = new Manufacturer();
        ferrari.setName("ferrai");
        ferrari.setCountry("italy");
        manufacturerDao.update(ferrari);
    }
}
