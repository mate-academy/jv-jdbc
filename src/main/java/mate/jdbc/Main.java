package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        for (Manufacturer manufacturer : manufacturers) {
                System.out.println(manufacturer);
        }
        ///////////////////////////////////////////////
        Manufacturer savedManufacturer = manufacturerDao
                .create(new Manufacturer("Toyota", "Japan"));
        System.out.println("savedManufacturer = " + savedManufacturer);
        ///////////////////////////////////////////////
        boolean deletedToyota = manufacturerDao.delete(savedManufacturer.getId());
        System.out.println("deleteToyota = " + deletedToyota);
        ///////////////////////////////////////////////
        boolean deleted10 = manufacturerDao.delete(10L);
        System.out.println("delete10 = " + deleted10);
        ///////////////////////////////////////////////
    }
}
