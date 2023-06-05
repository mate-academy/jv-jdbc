package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        //running a script that initializes the database
        ConnectionUtil.executeScript();
        List<Manufacturer> manufacturers = List.of(
                new Manufacturer("Ford", "Usa"),
                new Manufacturer("Mercedes", "Germany"),
                new Manufacturer("Lamborghini", "Italy"));
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        manufacturers.forEach(manufacturerDao::create);
        Manufacturer manufacturer = manufacturerDao.get(2L).orElseThrow();
        manufacturer.setName("Bmw");
        manufacturerDao.update(manufacturer);
        manufacturerDao.getAll().forEach(System.out::println);
        manufacturerDao.delete(manufacturer.getId());
    }
}
