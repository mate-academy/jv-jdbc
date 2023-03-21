package mate.jdbc;

import java.util.ArrayList;
import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    public static void main(String[] args) {
        ManufacturerDaoImpl manufacturerDao = (ManufacturerDaoImpl) injector.getInstance(ManufacturerDao.class);
        List<Manufacturer> machines = new ArrayList<>();
        machines.add(new Manufacturer("Hyundai", "South Korea"));
        machines.add(new Manufacturer("Renault", "France"));
        machines.add(new Manufacturer("Volkswagen", "Germany"));
        machines.forEach(manufacturerDao::create);
    }

}
