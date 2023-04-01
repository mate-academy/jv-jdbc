package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
        Manufacturer manufacturer1 = new Manufacturer("BMW", "Germany");
        Manufacturer manufacturer2 = new Manufacturer("Mazda", "Japan");
        manufacturerDao.create(manufacturer1);
        manufacturerDao.create(manufacturer2);
        System.out.println("Calling getAll():");
        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        for (Manufacturer manufacturer : allManufacturers) {
            System.out.println(manufacturer);
        }

        System.out.println("\nCalling get():");
        Optional<Manufacturer> manufacturer2FromDbOptional = manufacturerDao.get(2L);
        Manufacturer manufacturer2FromDb = manufacturer2FromDbOptional.get();
        System.out.println(manufacturerDao.get(manufacturer2FromDb.getId()));

        manufacturer2FromDb.setName("Toyota");
        manufacturerDao.update(manufacturer2FromDb);

        System.out.println("\nCalling get() after update:");
        System.out.println(manufacturerDao.get(manufacturer2FromDb.getId()));

        Optional<Manufacturer> manufacturer1FromDbOptional = manufacturerDao.get(1L);
        Manufacturer manufacturer1FromDb = manufacturer1FromDbOptional.get();
        manufacturerDao.delete(manufacturer1FromDb.getId());
        System.out.println("\nCalling getAll() after delete:");
        allManufacturers = manufacturerDao.getAll();
        for (Manufacturer manufacturer : allManufacturers) {
            System.out.println(manufacturer);
        }
    }
}
