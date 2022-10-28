package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer1 = new Manufacturer("ferrari", "italy");
        Manufacturer manufacturer2 = new Manufacturer("bmw", "germany");
        Manufacturer manufacturer3 = new Manufacturer("lexus", "japan");
        Manufacturer manufacturer4 = new Manufacturer("peugeot", "france");
        Manufacturer manufacturer5 = new Manufacturer("toyota", "japan");
        List<Manufacturer> manufacturers
                = List.of(manufacturer1, manufacturer2,
                manufacturer3, manufacturer4, manufacturer5);
        for (Manufacturer manufacturer : manufacturers) {
            Manufacturer newManufacturer
                    = manufacturerDao.create(manufacturer);
            System.out.println(newManufacturer);
        }
        System.out.println(manufacturerDao.delete(1L));
        Manufacturer manufacturer = new Manufacturer(5, "maibach", "DUBAI");
        System.out.println(manufacturerDao.update(manufacturer));
        System.out.println(manufacturerDao.get(5L));
        List<Manufacturer> newManufacturers = manufacturerDao.getAll();
        for (Manufacturer manufactur : newManufacturers) {
            System.out.println(manufactur);
        }

        Manufacturer shkoda = new Manufacturer("shkoda","sweden");
        System.out.println(manufacturerDao.create(shkoda));
    }
}
