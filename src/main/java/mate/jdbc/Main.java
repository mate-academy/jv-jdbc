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

        Optional<Manufacturer> manufacturerGetBmw = manufacturerDao.get(bmw.getId());
        System.out.println(manufacturerGetBmw);
        Optional<Manufacturer> manufacturerGetRenault = manufacturerDao.get(renault.getId());
        System.out.println(manufacturerGetRenault);

        boolean deleteRenault = manufacturerDao.delete(renault.getId());
        boolean deleteFord = manufacturerDao.delete(ford.getId());
        System.out.println(deleteRenault);
        System.out.println(deleteFord);
        System.out.println(manufacturerDao.getAll());

        Manufacturer zaz = new Manufacturer(volkswagen.getId(), "Zaz", "Ukraine");
        manufacturerDao.update(zaz);
        System.out.println(manufacturerDao.get(zaz.getId()));

        System.out.println(manufacturerDao.getAll());
    }
}
