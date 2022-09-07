package mate.jdbc;

import dao.ManufacturerDao;
import dao.impl.ManufacturerDaoImpl;
import java.util.List;
import model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
        List<Manufacturer> allManufacturer = manufacturerDao.getAll();
        for (Manufacturer manufacturer : allManufacturer) {
            System.out.println(manufacturer);
        }
        Manufacturer manufacturerBmw = new Manufacturer();
        manufacturerBmw.setName("BMW");
        manufacturerBmw.setCountry("Germany");
        Manufacturer savedManufacturer = manufacturerDao.create(manufacturerBmw);
        System.out.println(savedManufacturer);

        System.out.println(manufacturerDao.delete(savedManufacturer.getId()));
        manufacturerDao.getAll().forEach(System.out::println);

        System.out.println(manufacturerDao.get(manufacturerBmw.getId()));

        Manufacturer manufacturerRenault = new Manufacturer();
        manufacturerRenault.setName("Renault");
        manufacturerRenault.setCountry("Nevada");
        manufacturerRenault.setId(5L);
        System.out.println(manufacturerDao.update(manufacturerRenault));
        System.out.println("-------------------");
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
