package mate.jdbc;

import java.util.ArrayList;
import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        List<Manufacturer> manufacturerList = new ArrayList<>();
        Manufacturer audi = new Manufacturer();
        audi.setName("Audi");
        audi.setCountry("Germany");
        manufacturerList.add(audi);
        Manufacturer ford = new Manufacturer();
        ford.setName("Ford");
        ford.setCountry("USA");
        manufacturerList.add(ford);
        Manufacturer toyota = new Manufacturer();
        toyota.setName("Toyota");
        toyota.setCountry("Japan");
        manufacturerList.add(toyota);
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        manufacturerList.forEach(manufacturerDao::create);
        System.out.println(manufacturerDao.get(2L));
        manufacturerDao.delete(1L);
        System.out.println(manufacturerDao.get(1L));
        Manufacturer updateManufacturer = manufacturerList.get(2);
        updateManufacturer.setName("Bugatti");
        updateManufacturer.setCountry("France");
        updateManufacturer.setId(2L);
        manufacturerDao.update(updateManufacturer);
    }
}
