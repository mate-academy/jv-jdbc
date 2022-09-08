package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
        /////create/////
        Manufacturer toyota = manufacturerDao
                .create(new Manufacturer(null,"Toyota", "Japan"));
        System.out.println("savedManufacturer = " + toyota);
        /////get/////
        Optional<Manufacturer> optionalManufacturerFerrari = manufacturerDao.get(toyota.getId());
        optionalManufacturerFerrari.ifPresent(System.out::println);
        /////update/////
        Manufacturer toyotaWithFirstId = new Manufacturer(null,"Toyota", "Japan");
        toyotaWithFirstId.setId(toyota.getId());
        Manufacturer update = manufacturerDao.update(toyotaWithFirstId);
        System.out.println("update = " + update);
        /////delete/////
        boolean isDeletedToyota = manufacturerDao.delete(toyota.getId());
        System.out.println("deleteToyota = " + isDeletedToyota);
        /////getAll/////
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        for (Manufacturer manufacturer : manufacturers) {
            System.out.println(manufacturer);
        }
    }
}
