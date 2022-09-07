package mate.jdbc;

import java.util.List;
import java.util.Optional;
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
        Manufacturer toyota = manufacturerDao
                .create(new Manufacturer("Toyota", "Japan"));
        System.out.println("savedManufacturer = " + toyota);
        ///////////////////////////////////////////////
        boolean isDeletedToyota = manufacturerDao.delete(toyota.getId());
        System.out.println("deleteToyota = " + isDeletedToyota);
        ///////////////////////////////////////////////
        boolean isDeletedHonda = manufacturerDao.delete(10L);
        System.out.println("deleteHonda = " + isDeletedHonda);
        ///////////////////////////////////////////////
        Optional<Manufacturer> optionalManufacturerFerrari = manufacturerDao.get(5L);
        if (optionalManufacturerFerrari.isPresent()) {
            System.out.println(optionalManufacturerFerrari.get());
        }
        ///////////////////////////////////////////////
        Manufacturer toyotaWithFirstId = new Manufacturer("Toyota", "Japan");
        toyotaWithFirstId.setId(1L);
        Manufacturer update = manufacturerDao.update(toyotaWithFirstId);
        System.out.println("update = " + update);
    }
}
