package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final long ID_OF_DAEWOO = 1;
    private static final long ID_OF_SCODA = 2;
    private static final long ID_OF_LEXUS = 3;
    private static final long ID_OF_TOYOTA = 4;
    //    private static final Injector injector =
    //    Injector.getInstance("src/main/java/mate/jdbc");
    //    -don't work with my PC

    public static void main(String[] args) {
        //        ManufacturerDao manufacturerDao = (ManufacturerDao)
        //        injector.getInstance(ManufacturerDao.class);
        //        -don't work with my PC
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
        Manufacturer daewoo = new Manufacturer(ID_OF_DAEWOO, "DAEWOO", "South Korea");
        Manufacturer scoda = new Manufacturer(ID_OF_SCODA, "Scoda", "Czech Republic");
        Manufacturer lexus = new Manufacturer(ID_OF_LEXUS, "Lexus", "Japan");
        Manufacturer toyota = new Manufacturer(ID_OF_TOYOTA, "Toyota", "Japan");
        manufacturerDao.create(daewoo);
        manufacturerDao.create(scoda);
        manufacturerDao.create(lexus);
        manufacturerDao.create(toyota);
        daewoo.setName("DWO");
        manufacturerDao.update(daewoo);
        List<Manufacturer> manufacturersFromDb = manufacturerDao.getAll();
        Manufacturer toyotaFromDb = manufacturerDao.get(ID_OF_TOYOTA).orElseThrow();
        manufacturerDao.delete(ID_OF_LEXUS);
        if (manufacturersFromDb.contains(daewoo)
                && manufacturerDao.get(ID_OF_LEXUS).isEmpty()
                && toyotaFromDb.equals(toyota)) {
            System.out.println("CRUD in ManufacturerDaoImpl working well!");
        }
    }
}
