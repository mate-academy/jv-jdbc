package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

import java.util.List;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        List<Manufacturer> manufacturers = List.of(
                new Manufacturer("Dodge", "USA"),
                new Manufacturer("Renault", "France"),
                new Manufacturer("Peugeot", "France"),
                new Manufacturer("BMW", "Germany"),
                new Manufacturer("Mercedes", "Germany"),
                new Manufacturer("Hyundai", "Korea"),
                new Manufacturer("Subaru", "Japan")
        );
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturerToUpdate = new Manufacturer(2L,"Skoda", "Czechia");
        manufacturers.forEach(manufacturer -> manufacturerDao.create(manufacturer));

        manufacturerDao.get(4L);
        manufacturerDao.update(manufacturerToUpdate);
        manufacturerDao.delete(2L);
        manufacturerDao.delete(1L);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
