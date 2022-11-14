package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturerAudi = new Manufacturer("Audi", "Germany");
        Manufacturer manufacturerBmw = new Manufacturer("BMW", "Germany");
        Manufacturer manufacturerMercedes = new Manufacturer("Mercedes", "Germany");
        final Manufacturer manufacturerVolvo = new Manufacturer("Volvo", "Switzerland");
        manufacturerDao.create(manufacturerAudi);
        manufacturerDao.create(manufacturerBmw);
        manufacturerDao.create(manufacturerMercedes);
        Optional<Manufacturer> manufacturerAudiFromDB =
                manufacturerDao.get(manufacturerAudi.getId());
        System.out.println("Get audi from DB: " + manufacturerAudiFromDB);
        List<Manufacturer> manufacturerListAfterCreating = manufacturerDao.getAll();
        System.out.println("All rows from DB after creating: " + manufacturerListAfterCreating);
        manufacturerVolvo.setId(manufacturerBmw.getId());
        manufacturerDao.update(manufacturerVolvo);
        List<Manufacturer> manufacturerListAfterUpdate = manufacturerDao.getAll();
        System.out.println("All rows from DB after updating: " + manufacturerListAfterUpdate);
        manufacturerDao.delete(manufacturerMercedes.getId());
        List<Manufacturer> manufacturerListAfterDelete = manufacturerDao.getAll();
        System.out.println("All rows from DB after deleting: " + manufacturerListAfterDelete);
    }
}
