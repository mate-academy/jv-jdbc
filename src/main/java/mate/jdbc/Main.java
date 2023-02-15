package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        manufacturerDao.create(new Manufacturer(null, "Mercedes", "Germany"));
        manufacturerDao.create(new Manufacturer(null, "BMW", "Germany"));
        manufacturerDao.create(new Manufacturer(null, "Alfa Romeo", "Italy"));
        manufacturerDao.create(new Manufacturer(null, "Skoda", "Czech"));
        Manufacturer zazPlant = manufacturerDao.create(new Manufacturer(null, "ZAZ", "Belarus"));
        printList(manufacturerDao.getAll());
        System.out.println();

        Optional<Manufacturer> zazPlantFromDB = manufacturerDao.get(zazPlant.getId());
        System.out.println(zazPlantFromDB);
        System.out.println();

        zazPlant.setCountry("Ukraine");
        manufacturerDao.update(zazPlant);
        printList(manufacturerDao.getAll());
        System.out.println();

        manufacturerDao.delete(zazPlant.getId());
        manufacturerDao.getAll();
        printList(manufacturerDao.getAll());
    }

    private static void printList(List<Manufacturer> list) {
        for (Manufacturer manufacturer: list) {
            System.out.println(manufacturer);
        }
    }
}
