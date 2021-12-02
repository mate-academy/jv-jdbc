package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer daewoo = manufacturerDao.create(new Manufacturer("DAEWOO", "South Korea"));
        Manufacturer scoda = manufacturerDao.create( new Manufacturer("Scoda", "Czech Republic"));
        Manufacturer lexus = manufacturerDao.create(new Manufacturer("Lexus", "Japan"));
        Manufacturer toyota = manufacturerDao.create(new Manufacturer("Toyota", "Japan"));
        daewoo.setName("DWO");
        manufacturerDao.update(daewoo);
        Manufacturer daewooFromDb = manufacturerDao.get(daewoo.getId()).get();
        List<Manufacturer> manufacturersFromDb = manufacturerDao.getAll();
        if (daewoo.equals(daewooFromDb) || manufacturersFromDb.contains(scoda) || manufacturerDao.delete(lexus.getId())) {
            System.out.println("dao works good");
        }
    }
}
