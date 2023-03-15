package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.models.Manufacturer;

public class Main {
    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();

        Optional<Manufacturer> manufacturerOptional = manufacturerDao.get(13L);
        System.out.println(manufacturerOptional.orElseThrow(()-> new RuntimeException("Can't find manufacturer")));


//        Manufacturer manufacturer = new Manufacturer();
//        manufacturer.setName("Tavria Slavuta");
//        manufacturer.setCountry("Ukraine");
//        Manufacturer savedManufacturer = manufacturerDao.create(manufacturer);
//
//        System.out.println(savedManufacturer);
//
//        Manufacturer updateManufacturer = new Manufacturer();
//        updateManufacturer.setId(12L);
//        updateManufacturer.setCountry("India");
//        updateManufacturer.setName("Mahindra");
//
//        System.out.println(manufacturerDao.update(updateManufacturer));


//        System.out.println(manufacturerDao.delete(savedManufacturer.getId()));

//        List<Manufacturer> allManufacturersList = manufacturerDao.getAll();
//        for (Manufacturer manufacturer : allManufacturersList) {
//            System.out.println(manufacturer);
//        }
    }
}
