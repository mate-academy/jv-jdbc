package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    
    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer bmv = new Manufacturer();
        bmv.setName("BMV");
        bmv.setCountry("Germany");
        manufacturerDao.create(bmv);
    
        Manufacturer audi = new Manufacturer();
        audi.setName("AUDI");
        audi.setCountry("Germany");
        manufacturerDao.create(audi);
        
        Manufacturer vw = new Manufacturer();
        vw.setId(2L);
        vw.setName("Volkswagen");
        vw.setCountry("Germany");
        manufacturerDao.update(vw);
    
        Optional<Manufacturer> getManufacturer = manufacturerDao.get(2L);
        System.out.println(getManufacturer);
    
        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        System.out.println(allManufacturers);
        
        manufacturerDao.delete(1L);
    }
}
