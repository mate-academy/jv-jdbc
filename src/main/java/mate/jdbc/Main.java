package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao)
                INJECTOR.getInstance(ManufacturerDao.class);
        Manufacturer manufacturerReno = manufacturerDao
                .create(new Manufacturer("Reno", "France"));
        Manufacturer manufacturerBmw = manufacturerDao
                .create(new Manufacturer("BMW", "Germany"));
        Manufacturer manufacturerByd = manufacturerDao
                .create(new Manufacturer("BYD", "China"));
        manufacturerReno.setName("Dacia");
        manufacturerDao.update(manufacturerReno);
        manufacturerDao.delete(manufacturerBmw.getId());
        List<Manufacturer> manufacturerList = manufacturerDao.getAll();
        manufacturerList.forEach(System.out::println);
    }
}
