package mate.jdbc;

import java.util.Arrays;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer peugeot = new Manufacturer(null, "Peugeot", "France");
        Manufacturer renault = new Manufacturer(null, "Renault", "France");
        Manufacturer bmw = new Manufacturer(null, "BMW", "Germany");
        Manufacturer volkswagen = new Manufacturer(null, "Volkswagen", "Germany");
        Manufacturer ford = new Manufacturer(null, "Ford", "USA");

        Manufacturer[] manufacturers = {peugeot, renault, bmw, volkswagen, ford};
        Arrays.stream(manufacturers).forEach(manufacturerDao::create);
        System.out.println(manufacturerDao.getAll());

        Optional<Manufacturer> manufacturerGetBmw = manufacturerDao.get(5L);
        System.out.println(manufacturerGetBmw);
        Optional<Manufacturer> manufacturerGetEmpty = manufacturerDao.get(1L);
        System.out.println(manufacturerGetEmpty);
        Optional<Manufacturer> manufacturerGetRenault = manufacturerDao.get(2L);
        System.out.println(manufacturerGetRenault);

        boolean delete = manufacturerDao.delete(1L);
        boolean deleteRenault = manufacturerDao.delete(2L);
        System.out.println(delete);
        System.out.println(deleteRenault);
        System.out.println(manufacturerDao.getAll());

        manufacturerDao.update(new Manufacturer(3L, "Zaz", "Ukraine"));
        System.out.println(manufacturerDao.get(3L));

        System.out.println(manufacturerDao.getAll());
    }
}
